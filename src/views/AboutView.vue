<script setup lang="ts">
import { useI18n } from 'vue-i18n';
import { onMounted } from 'vue';
import { useTheme } from '../composables/useTheme';
import Logo from '../components/Logo.vue';
import TextLogo from '../components/TextLogo.vue';
import TechBadge from '../components/TechBadge.vue';
import TechIcon from '../components/icons/TechIcon.vue';

const { t } = useI18n();
const { theme } = useTheme();

const techStacks = [
  {
    category: '前端技术 (Frontend)',
    icon: 'mdi:monitor-dashboard',
    items: [
      { name: 'Vue 3', badge: 'Composition API' },
      { name: 'TypeScript', badge: '5.0+' },
      { name: 'Vite', badge: 'Build Tool' },
      { name: 'SCSS', badge: 'Styling' },
      { name: 'Pinia', badge: 'State' },
      { name: 'Glassmorphism', badge: 'UI Design' }
    ]
  },
  {
    category: '后端技术 (Backend)',
    icon: 'mdi:server',
    items: [
      { name: 'Spring Boot', badge: '3.5.7' },
      { name: 'MyBatis Plus', badge: 'ORM' },
      { name: 'Sa-Token', badge: 'Auth' },
      { name: 'Elasticsearch', badge: 'Search' },
      { name: 'SpringDoc', badge: 'API Docs' }
    ]
  },
  {
    category: '数据与设施 (Infra)',
    icon: 'mdi:database',
    items: [
      { name: 'MySQL', badge: '8.0' },
      { name: 'Redis', badge: 'Cache' },
      { name: 'JDK', badge: '21' },
      { name: 'Docker', badge: 'Deploy' }
    ]
  }
];

const features = [
  {
    title: '沉浸式阅读',
    desc: 'Markdown 实时渲染、代码高亮、数学公式支持，提供极致的阅读体验。',
    icon: 'mdi:book-open-page-variant'
  },
  {
    title: '全端响应式',
    desc: '完美适配 Desktop、Tablet、Mobile 等各种设备，随时随地访问。',
    icon: 'mdi:responsive'
  },
  {
    title: '全文检索',
    desc: '集成 Elasticsearch，提供毫秒级的高亮搜索体验，快速定位内容。',
    icon: 'mdi:magnify'
  },
  {
    title: '社区互动',
    desc: '支持多级评论、点赞、收藏等互动功能，构建活跃的技术社区。',
    icon: 'mdi:account-group'
  }
];

const quickStartSteps = [
  {
    num: '01',
    title: '获取代码',
    content: '分别克隆前后端分支到本地工作目录：',
    code: 'git clone -b backend https://github.com/patton174/Rookie-Blog.git backend\ngit clone -b frontend https://github.com/patton174/Rookie-Blog.git frontend'
  },
  {
    num: '02',
    title: '环境准备',
    content: '确保您的开发环境满足以下最低要求：',
    list: ['JDK 21 (推荐) 或 17+', 'Node.js 18+', 'MySQL 8.0+', 'Redis 7.0+']
  },
  {
    num: '03',
    title: '启动服务',
    content: '后端配置数据库后启动 Application；前端安装依赖后启动：',
    code: 'cd frontend\nnpm install\nnpm run dev'
  }
];

const projectStructure = `Rookie-Blog/
├── backend/              # 后端项目
│   ├── rookie-blog-app/       # 启动模块
│   ├── rookie-blog-web/       # 接口层
│   ├── rookie-blog-service/   # 业务层
│   └── ...
├── frontend/             # 前端项目
│   ├── src/
│   │   ├── api/         # API接口
│   │   ├── views/       # 页面组件
│   │   ├── components/  # 公共组件
│   │   └── ...
└── README.md             # 说明文档`;

onMounted(() => {
  window.scrollTo(0, 0);
});
</script>

<template>
  <div class="about-page">
    <div class="container content-layout">
      
      <!-- Hero Header -->
      <header class="about-header fade-in-up">
        <div class="brand-wrapper">
          <Logo :width="100" :height="100" class="brand-icon" />
          <TextLogo 
            :width="300" 
            :height="50" 
            :variant="theme === 'dark' ? 'dark' : 'light'" 
            idSuffix="about"
            class="brand-text"
          />
        </div>
        <p class="page-subtitle">基于 Spring Boot 3 + Vue 3 的现代化全栈个人博客系统</p>
        
        <!-- Tech Badges (Optimized: Local CSS Component instead of External Images to fix CLS & Performance) -->
        <div class="badges-container">
          <TechBadge label="Spring Boot" message="3.5.7" color="6DB33F" logo="simple-icons:springboot" />
          <TechBadge label="Vue.js" message="3.4+" color="4FC08D" logo="simple-icons:vuedotjs" />
          <TechBadge label="TypeScript" message="5.0+" color="3178C6" logo="simple-icons:typescript" />
          <TechBadge label="JDK" message="21" color="E53525" logo="simple-icons:openjdk" />
          <TechBadge label="MySQL" message="8.0" color="4479A1" logo="simple-icons:mysql" />
          <TechBadge label="Redis" message="7.0" color="DC382D" logo="simple-icons:redis" />
          <TechBadge label="Elasticsearch" message="8.13.2" color="005571" logo="simple-icons:elasticsearch" />
          <TechBadge label="Vite" message="5.0+" color="646CFF" logo="simple-icons:vite" />
          <!-- <TechBadge label="Sass" message="Latest" color="CC6699" logo="simple-icons:sass" /> -->
          <!-- <TechBadge label="License" message="MIT" color="green" logo="simple-icons:opensourceinitiative" /> -->
        </div>
      </header>

      <!-- Intro Card -->
      <section class="intro-section glass-panel fade-in-up stagger-delay-1">
        <div class="intro-bg-glow"></div>
        <div class="intro-content">
           <div class="intro-visual">
              <div class="visual-circle">
                <TechIcon name="sparkles" :size="64" class="floating-icon" style="color: #FFD700" />
              </div>
           </div>
           <div class="intro-text-wrapper">
              <h2 class="section-title-left">
                <span class="title-icon">✨</span> 项目介绍
              </h2>
              <p class="main-desc">
                <strong>Rookie Blog</strong> 是一款融合了 <span class="highlight">现代化设计</span> 与 <span class="highlight">前沿技术</span> 的全栈博客系统。
              </p>
              <p class="sub-desc">
                我们致力于打破传统博客的单调，采用 <strong>Glassmorphism</strong> 玻璃拟态设计，为您带来沉浸式的阅读体验。
                底层架构坚实可靠，基于 <span class="tech-pill">Spring Boot 3</span> 与 <span class="tech-pill">Vue 3</span> 深度驱动，集成了 <span class="tech-pill">Elasticsearch</span> 全文检索等企业级特性。
              </p>
           </div>
        </div>
      </section>

      <!-- Tech Stack Grid -->
      <section class="tech-stack-section fade-in-up stagger-delay-2">
         <h2 class="section-title-center">技术栈概览</h2>
         <div class="tech-grid">
            <div v-for="stack in techStacks" :key="stack.category" class="tech-card glass-panel">
               <div class="tech-header">
                  <div class="tech-icon-wrapper">
                    <TechIcon :name="stack.icon" :size="24" color="white" />
                  </div>
                  <h3>{{ stack.category }}</h3>
               </div>
               <ul class="tech-list">
                  <li v-for="item in stack.items" :key="item.name">
                     <span class="tech-name">{{ item.name }}</span>
                     <span class="tech-badge">{{ item.badge }}</span>
                  </li>
               </ul>
            </div>
         </div>
      </section>

      <!-- Features Grid -->
      <section class="features-section fade-in-up stagger-delay-3">
         <h2 class="section-title-center">核心功能</h2>
         <div class="features-grid">
            <div v-for="feature in features" :key="feature.title" class="feature-card glass-panel">
               <div class="feature-icon">
                  <TechIcon :name="feature.icon" :size="32" color="white" />
               </div>
               <h3>{{ feature.title }}</h3>
               <p>{{ feature.desc }}</p>
            </div>
         </div>
      </section>

      <!-- Project Structure -->
      <section class="structure-section glass-panel fade-in-up stagger-delay-4">
         <h2 class="section-title">
            <TechIcon name="sitemap" :size="24" color="#666" style="vertical-align: bottom; margin-right: 8px;" />
            项目结构
         </h2>
         <div class="code-block">
             <pre><code>{{ projectStructure }}</code></pre>
         </div>
      </section>

      <!-- Quick Start -->
      <section class="quick-start-section fade-in-up stagger-delay-4">
         <h2 class="section-title-center">快速开始</h2>
         <div class="steps-container">
            <div v-for="step in quickStartSteps" :key="step.num" class="step-card glass-panel">
               <div class="step-header">
                  <span class="step-num">{{ step.num }}</span>
                  <h3>{{ step.title }}</h3>
               </div>
               <div class="step-content">
                  <p>{{ step.content }}</p>
                  <ul v-if="step.list" class="step-list">
                     <li v-for="li in step.list" :key="li">{{ li }}</li>
                  </ul>
                  <div v-if="step.code" class="step-code">
                     <pre><code>{{ step.code }}</code></pre>
                  </div>
               </div>
            </div>
         </div>
      </section>

      <!-- Footer / Connect -->
      <footer class="about-footer fade-in-up stagger-delay-2">
        <div class="glass-panel connect-card">
          <div class="footer-content">
             <div class="footer-text">
                <h3>Made by Rookie Blog Team</h3>
                <p>致力于打造高质量的开源项目，与社区共同成长。</p>
             </div>
             <div class="footer-actions">
               <a href="https://github.com/patton174/Rookie-Blog" target="_blank" class="github-btn">
                 <svg height="24" width="24" viewBox="0 0 16 16" fill="currentColor">
                    <path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"></path>
                 </svg>
                 Star on GitHub
               </a>
             </div>
          </div>
        </div>
      </footer>

    </div>
  </div>
</template>

<style lang="scss" scoped>
@use '../styles/variables' as *;

.about-page {
  padding-top: 100px;
  min-height: 100vh;
  padding-bottom: $spacing-xl;
  background: transparent;
}

.content-layout {
  max-width: 1000px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: $spacing-xl;
}

/* Header Styles */
.about-header {
  text-align: center;
  margin-bottom: $spacing-lg;
  
  .brand-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: $spacing-md;
    margin-bottom: $spacing-md;
    
    .brand-icon {
      filter: drop-shadow(0 10px 20px rgba(0,0,0,0.1));
      transition: transform 0.5s ease;
      
      &:hover {
        transform: rotate(10deg) scale(1.05);
      }
      
      @media (max-width: $breakpoint-mobile) {
        width: 80px;
        height: 80px;
      }
    }

    .brand-text {
      filter: drop-shadow(0 5px 15px rgba(0,0,0,0.05));
      transition: transform 0.5s ease;
      
      &:hover {
        transform: scale(1.02);
      }
      
      @media (max-width: $breakpoint-mobile) {
        width: 240px;
        height: 40px;
      }
    }

    @media (max-width: $breakpoint-mobile) {
      flex-direction: column;
      gap: $spacing-sm;
    }
  }
  
  .page-subtitle {
    font-size: 1.2rem;
    color: $color-text-secondary;
    margin-bottom: $spacing-lg;
  }

  .badges-container {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    gap: 8px;
    
    /* TechBadge transition */
    :deep(.tech-badge) {
      transition: transform 0.2s ease;
      &:hover {
        transform: translateY(-2px);
      }
    }
  }
}

/* Glass Panel Generic */
.glass-panel {
  padding: $spacing-lg;
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.6);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.4);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  
  :global(.dark) & {
    background: rgba(30, 30, 30, 0.6);
    border-color: rgba(255, 255, 255, 0.1);
  }
}

/* Intro Section Redesign */
.intro-section {
  position: relative;
  overflow: hidden;
  
  .intro-bg-glow {
    position: absolute;
    top: -50%;
    right: -20%;
    width: 300px;
    height: 300px;
    background: radial-gradient(circle, rgba($color-accent-primary, 0.15) 0%, transparent 70%);
    filter: blur(40px);
    z-index: 0;
  }

  .intro-content {
    position: relative;
    z-index: 1;
    display: flex;
    align-items: center;
    gap: $spacing-xl;
    
    @media (max-width: $breakpoint-mobile) {
      flex-direction: column;
      text-align: center;
      gap: $spacing-md;
    }
  }

  .intro-visual {
    flex-shrink: 0;
    
    .visual-circle {
      width: 100px;
      height: 100px;
      border-radius: 30px;
      background: linear-gradient(135deg, rgba(255,255,255,0.4), rgba(255,255,255,0.1));
      border: 1px solid rgba(255,255,255,0.3);
      display: flex;
      align-items: center;
      justify-content: center;
      box-shadow: 0 15px 35px rgba(0,0,0,0.1);
      transform: rotate(-5deg);
      transition: transform 0.3s ease;
      
      &:hover {
        transform: rotate(0deg) scale(1.05);
      }
      
      .floating-icon {
        animation: float 3s ease-in-out infinite;
        filter: drop-shadow(0 5px 15px rgba(0,0,0,0.1));
      }
    }
  }

  .intro-text-wrapper {
    flex: 1;
    
    .section-title-left {
      font-size: 1.8rem;
      font-weight: 800;
      margin-bottom: $spacing-md;
      color: $color-text-primary;
      display: flex;
      align-items: center;
      gap: 10px;
      
      @media (max-width: $breakpoint-mobile) {
        justify-content: center;
      }
      
      .title-icon {
        font-size: 1.5rem;
      }
    }
    
    .main-desc {
      font-size: 1.1rem;
      color: $color-text-primary;
      margin-bottom: $spacing-md;
      line-height: 1.6;
      
      .highlight {
        color: $color-accent-primary;
        font-weight: 600;
      }
    }
    
    .sub-desc {
      font-size: 0.95rem;
      color: $color-text-secondary;
      line-height: 1.8;
      
      .tech-pill {
        display: inline-block;
        padding: 0 6px;
        background: rgba($color-accent-primary, 0.1);
        color: $color-accent-primary;
        border-radius: 4px;
        font-size: 0.85rem;
        font-weight: 500;
        margin: 0 2px;
      }
    }
  }
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-8px); }
}

/* Section Titles */
.section-title-center {
  text-align: center;
  font-size: 1.8rem;
  margin-bottom: $spacing-lg;
  font-weight: 700;
  color: $color-text-primary;
}

.section-title {
  display: flex;
  align-items: center;
  font-size: 1.5rem;
  margin-bottom: $spacing-md;
  color: $color-text-primary;
}

/* Tech Grid */
.tech-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: $spacing-lg;
  
  .tech-card {
    padding: $spacing-lg;
    background: rgba(255, 255, 255, 0.4); // slightly lighter than glass-panel
    
    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 15px 30px rgba(0,0,0,0.1);
    }
    
    .tech-header {
      display: flex;
      align-items: center;
      gap: $spacing-md;
      margin-bottom: $spacing-md;
      padding-bottom: $spacing-md;
      border-bottom: 1px solid rgba(0,0,0,0.05);
      
      .tech-icon-wrapper {
        width: 40px;
        height: 40px;
        background: $gradient-primary;
        border-radius: 10px;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      
      h3 {
        font-size: 1.2rem;
        font-weight: 600;
        margin: 0;
      }
    }
    
    .tech-list {
      list-style: none;
      padding: 0;
      margin: 0;
      
      li {
        display: flex;
        justify-content: space-between;
        padding: 8px 0;
        border-bottom: 1px dashed rgba(0,0,0,0.05);
        
        &:last-child { border-bottom: none; }
        
        .tech-name {
          font-weight: 500;
          color: $color-text-primary;
        }
        
        .tech-badge {
          font-size: 0.8rem;
          padding: 2px 8px;
          background: rgba($color-accent-primary, 0.1);
          color: $color-accent-primary;
          border-radius: 10px;
        }
      }
    }
  }
}

/* Features Grid */
.features-grid {
  display: grid;
  // Use a fixed 2-column layout by default for symmetry (2x2)
  grid-template-columns: repeat(2, 1fr); 
  gap: $spacing-lg;
  
  // On mobile, switch to single column
  @media (max-width: $breakpoint-mobile) {
    grid-template-columns: 1fr;
  }
  
  // On very large screens where 4 items can fit comfortably in one row
  @media (min-width: 1100px) {
    grid-template-columns: repeat(4, 1fr);
  }
  
  .feature-card {
    text-align: center;
    padding: $spacing-lg;
    background: linear-gradient(145deg, rgba(255,255,255,0.5), rgba(255,255,255,0.2));
    
    &:hover {
      transform: translateY(-5px);
      .feature-icon {
        transform: scale(1.1) rotate(5deg);
      }
    }
    
    .feature-icon {
      width: 60px;
      height: 60px;
      margin: 0 auto $spacing-md;
      background: $gradient-primary;
      border-radius: 16px;
      display: flex;
      align-items: center;
      justify-content: center;
      transition: transform 0.3s ease;
      box-shadow: 0 10px 20px rgba($color-accent-primary, 0.3);
    }
    
    h3 {
      font-size: 1.2rem;
      margin-bottom: $spacing-sm;
      color: $color-text-primary;
    }
    
    p {
      font-size: 0.9rem;
      color: $color-text-secondary;
      line-height: 1.5;
    }
  }
}

/* Structure Section */
.structure-section {
  .code-block {
    background: #1e1e1e;
    border-radius: 12px;
    padding: $spacing-md;
    overflow-x: auto;
    
    pre {
      margin: 0;
      font-family: $font-family-code;
      font-size: 0.9rem;
      color: #d4d4d4;
      line-height: 1.5;
    }
  }
}

/* Quick Start Section */
.quick-start-section {
  .steps-container {
    display: flex;
    flex-direction: column;
    gap: $spacing-lg;
  }
  
  .step-card {
    display: flex;
    gap: $spacing-lg;
    align-items: flex-start;
    
    @media (max-width: $breakpoint-mobile) {
      flex-direction: column;
    }
    
    .step-header {
      flex-shrink: 0;
      display: flex;
      align-items: center;
      gap: $spacing-md;
      min-width: 200px;
      
      .step-num {
        font-size: 2.5rem;
        font-weight: 800;
        color: rgba($color-accent-primary, 0.2);
        line-height: 1;
      }
      
      h3 {
        font-size: 1.2rem;
        margin: 0;
        color: $color-text-primary;
      }
    }
    
    .step-content {
      flex-grow: 1;
      width: 100%;
      
      p {
        margin-bottom: $spacing-sm;
        color: $color-text-secondary;
      }
      
      .step-list {
        list-style: disc;
        padding-left: 20px;
        margin-bottom: $spacing-md;
        color: $color-text-secondary;
        
        li { margin-bottom: 4px; }
      }
      
      .step-code {
        background: #1e1e1e;
        padding: $spacing-md;
        border-radius: 8px;
        overflow-x: auto;
        
        pre {
          margin: 0;
          font-family: $font-family-code;
          font-size: 0.85rem;
          color: #d4d4d4;
          line-height: 1.5;
        }
      }
    }
  }
}

/* Connect/Footer Styles */
.connect-card {
  margin-top: $spacing-xl;
  text-align: center;
  background: linear-gradient(135deg, rgba($color-accent-primary, 0.05), rgba($color-accent-secondary, 0.05));
  
  .footer-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    
    @media (max-width: $breakpoint-mobile) {
      flex-direction: column;
      gap: $spacing-md;
    }
    
    .footer-text {
      text-align: left;
      @media (max-width: $breakpoint-mobile) { text-align: center; }
      
      h3 { margin-bottom: 4px; }
      p { color: $color-text-secondary; font-size: 0.9rem; }
    }
    
    .github-btn {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      padding: 10px 24px;
      background: #24292e;
      color: white;
      text-decoration: none;
      border-radius: 30px;
      font-weight: 600;
      transition: all 0.3s ease;
      box-shadow: 0 4px 12px rgba(0,0,0,0.2);
      
      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 20px rgba(0,0,0,0.3);
        background: black;
      }
      
      :global(.dark) & {
        background: white;
        color: black;
        &:hover { background: #f0f0f0; }
      }
    }
  }
}

/* Animation Utilities */
.fade-in-up {
  animation: fadeInUp 0.8s cubic-bezier(0.2, 0.8, 0.2, 1) forwards;
  opacity: 0;
  transform: translateY(30px);
}

.stagger-delay-1 { animation-delay: 0.1s; }
.stagger-delay-2 { animation-delay: 0.2s; }
.stagger-delay-3 { animation-delay: 0.3s; }
.stagger-delay-4 { animation-delay: 0.4s; }

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>