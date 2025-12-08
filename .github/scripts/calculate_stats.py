#!/usr/bin/env python3
import os
import sys
from collections import defaultdict
from datetime import datetime
from github import Github
import re

def get_contributor_stats():
    """获取backend和frontend分支的PR贡献者统计"""
    token = os.getenv('GITHUB_TOKEN')
    repo_name = os.getenv('GITHUB_REPOSITORY')
    target_file = os.getenv('TARGET_FILE', 'README.md')
    
    if not token:
        print("Error: GITHUB_TOKEN not found")
        sys.exit(1)
        
    g = Github(token)
    repo = g.get_repo(repo_name)
    
    # 统计贡献者
    contributors = defaultdict(int)
    
    # 获取backend分支的已合并PR
    try:
        backend_prs = repo.get_pulls(state='closed', base='backend', sort='created')
        for pr in backend_prs:
            if pr.merged:
                contributor = pr.user.login
                contributors[contributor] += 1
    except Exception as e:
        print(f"Warning: Could not fetch backend PRs: {e}")
    
    # 获取frontend分支的已合并PR
    try:
        frontend_prs = repo.get_pulls(state='closed', base='frontend', sort='created')
        for pr in frontend_prs:
            if pr.merged:
                contributor = pr.user.login
                contributors[contributor] += 1
    except Exception as e:
        print(f"Warning: Could not fetch frontend PRs: {e}")
    
    # 按贡献次数排序
    sorted_contributors = sorted(
        contributors.items(), 
        key=lambda x: x[1], 
        reverse=True
    )
    
    return sorted_contributors

def update_readme(contributors):
    """更新README.md文件"""
    target_file = os.getenv('TARGET_FILE', 'README.md')
    
    # 读取现有内容
    if os.path.exists(target_file):
        with open(target_file, 'r', encoding='utf-8') as f:
            content = f.read()
    else:
        content = ""
    
    # 创建贡献者表格 (Grid Layout)
    timestamp = datetime.now().strftime('%Y-%m-%d')
    
    # 头部样式
    stats_section = f"""
<br>

## <img src="https://api.iconify.design/mdi:account-group.svg?color=%23000000" width="24" height="24" valign="bottom"> 贡献者风采

感谢每一位参与 **Rookie Blog** 开发的贡献者，是你们让这个项目变得更好。

<table align="center" border="0">
"""
    
    columns = 10
    for i, (contributor, count) in enumerate(contributors):
        if i % columns == 0:
            stats_section += "  <tr>\n"
        
        # 贡献者信息
        profile_url = f"https://github.com/{contributor}"
        avatar_url = f"https://github.com/{contributor}.png?size=100"
        
        stats_section += f"""    <td align="center">
      <a href="{profile_url}" title="{contributor}">
        <img src="{avatar_url}" width="50" height="50" alt="{contributor}" style="border-radius: 50%;">
      </a>
    </td>
"""
        
        if (i + 1) % columns == 0:
            stats_section += "  </tr>\n"
    
    # 补全最后一行
    if len(contributors) % columns != 0:
        stats_section += "  </tr>\n"
        
    stats_section += "</table>\n"
    stats_section += f"\n<p align='right'><sub>Last updated: {timestamp}</sub></p>\n"
    
    # 使用标记来替换内容
    start_marker = "<!-- CONTRIBUTOR_STATS_START -->"
    end_marker = "<!-- CONTRIBUTOR_STATS_END -->"
    
    if start_marker in content and end_marker in content:
        # 替换现有内容
        pattern = f"{start_marker}.*?{end_marker}"
        new_section = f"{start_marker}\n{stats_section}\n{end_marker}"
        content = re.sub(pattern, new_section, content, flags=re.DOTALL)
    else:
        # 在文件末尾添加新内容
        content += f"\n\n{start_marker}\n{stats_section}\n{end_marker}"
    
    # 写回文件
    with open(target_file, 'w', encoding='utf-8') as f:
        f.write(content)
    
    print(f"✅ 已更新 {target_file} 文件")

def main():
    try:
        print("开始统计贡献者数据...")
        contributors = get_contributor_stats()
        
        if not contributors:
            print("⚠️ 没有找到贡献者数据")
            return
        
        print(f"找到 {len(contributors)} 位贡献者")
        for i, (contributor, count) in enumerate(contributors[:10], 1):
            print(f"{i}. @{contributor}: {count} 个合并PR")
        
        update_readme(contributors)
        print("✅ 统计完成！")
        
    except Exception as e:
        print(f"❌ 发生错误: {str(e)}")
        sys.exit(1)

if __name__ == "__main__":
    main()
