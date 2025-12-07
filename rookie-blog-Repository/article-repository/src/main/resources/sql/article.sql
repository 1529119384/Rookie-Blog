-- Rookie Blog Article Module Schema
-- Version: v1
-- Date: 2025-12-03
-- MySQL 8.0, utf8mb4

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- =====================================================================
-- Table: article (文章基础信息)
-- =====================================================================
CREATE TABLE IF NOT EXISTS article (
  id              CHAR(32)        NOT NULL COMMENT '文章ID，建议UUID32',
  title           VARCHAR(255)    NOT NULL COMMENT '文章标题',
  author_id       CHAR(32)        NOT NULL COMMENT '作者ID（与用户模块ID一致）',
  status          ENUM('draft','published','archived') NOT NULL DEFAULT 'draft' COMMENT '文章状态',
  summary         VARCHAR(1024)   NULL     COMMENT '文章摘要',
  visibility      ENUM('public','private','followers') NOT NULL DEFAULT 'public' COMMENT '可见性',
  is_top          TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '是否置顶',
  slug            VARCHAR(255)    NULL     COMMENT 'SEO友好别名（唯一）',
  cover_url       VARCHAR(512)    NULL     COMMENT '封面图URL',
  language        VARCHAR(16)     NULL     COMMENT '语言代码，如 zh_CN / en_US',
  meta_title      VARCHAR(255)    NULL     COMMENT 'SEO meta标题',
  meta_description VARCHAR(512)   NULL     COMMENT 'SEO meta描述',
  canonical_url   VARCHAR(512)    NULL     COMMENT '规范URL',
  source_url      VARCHAR(512)    NULL     COMMENT '来源链接（转载）',
  source_type     ENUM('original','repost') NOT NULL DEFAULT 'original' COMMENT '来源类型：原创/转载',
  reading_time_minutes INT        NULL     COMMENT '预计阅读时长（分钟）',
  word_count_total INT            NULL     COMMENT '总字数统计缓存',
  allow_comment   TINYINT(1)      NOT NULL DEFAULT 1 COMMENT '是否允许评论',
  is_reviewed     TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '是否已审核',
  review_status   ENUM('pending','approved','rejected') NOT NULL DEFAULT 'pending' COMMENT '审核状态',
  review_reason   VARCHAR(512)    NULL     COMMENT '审核原因',
  scheduled_publish_at DATETIME   NULL     COMMENT '定时发布时间',
  featured_at     DATETIME        NULL     COMMENT '加精时间',
  publish_at      DATETIME        NULL     COMMENT '发布时间',
  update_at       DATETIME        NULL     COMMENT '内容更新时间（与审计updated_at区分）',
  created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  created_by      CHAR(32)        NULL     COMMENT '创建人',
  updated_by      CHAR(32)        NULL     COMMENT '更新人',
  deleted_at      DATETIME        NULL     COMMENT '软删除时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_article_slug (slug),
  KEY idx_article_author_id (author_id),
  KEY idx_article_status_publish (status, publish_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章基础信息';

-- =====================================================================
-- Table: article_chapter (章节元数据)
-- =====================================================================
CREATE TABLE IF NOT EXISTS article_chapter (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '章节ID',
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  chapter_order   INT             NOT NULL COMMENT '章节顺序；0为虚拟章节（前言）',
  level           TINYINT         NOT NULL COMMENT '标题层级：0=虚拟章节，1~6对应h1~h6',
  title           VARCHAR(512)    NULL     COMMENT '章节标题文本',
  anchor          VARCHAR(255)    NULL     COMMENT '章节锚点（URL片段）',
  parent_id       BIGINT UNSIGNED NULL     COMMENT '父章节ID（用于层级结构；虚拟章节NULL）',
  path            VARCHAR(255)    NULL     COMMENT '物化路径（如 1/3/5）',
  start_offset    INT             NULL     COMMENT '在原Markdown中的开始位置',
  end_offset      INT             NULL     COMMENT '在原Markdown中的结束位置',
  hash            CHAR(64)        NULL     COMMENT '章节内容哈希（用于变更检测）',
  created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_chapter_article_anchor (article_id, anchor),
  KEY idx_chapter_article_order (article_id, chapter_order),
  KEY idx_chapter_article_level (article_id, level),
  KEY idx_chapter_parent_id (parent_id),
  CONSTRAINT fk_chapter_article_id FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE,
  CONSTRAINT fk_chapter_parent_id FOREIGN KEY (parent_id) REFERENCES article_chapter (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章章节元数据';

-- =====================================================================
-- Table: article_content (文章内容)
-- =====================================================================
CREATE TABLE IF NOT EXISTS article_content (
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  content_md      LONGTEXT        NULL     COMMENT 'Markdown内容',
  content_html    LONGTEXT        NULL     COMMENT 'HTML内容（渲染缓存）',
  words_count     INT             NULL     COMMENT '字数统计',
  updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (article_id),
  CONSTRAINT fk_content_article_id FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章内容';

-- =====================================================================
-- Table: category / tag / relations
-- =====================================================================
CREATE TABLE IF NOT EXISTS category (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  name            VARCHAR(255)    NOT NULL COMMENT '分类名称',
  code            VARCHAR(128)    NOT NULL COMMENT '分类唯一编码',
  parent_id       BIGINT UNSIGNED NULL     COMMENT '父分类ID',
  path            VARCHAR(255)    NULL     COMMENT '物化路径',
  created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_category_code (code),
  KEY idx_category_parent_id (parent_id),
  CONSTRAINT fk_category_parent_id FOREIGN KEY (parent_id) REFERENCES category (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章分类';

CREATE TABLE IF NOT EXISTS tag (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  name            VARCHAR(255)    NOT NULL COMMENT '标签名称',
  code            VARCHAR(128)    NOT NULL COMMENT '标签唯一编码',
  created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_tag_code (code),
  KEY idx_tag_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章标签';

CREATE TABLE IF NOT EXISTS article_category (
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  category_id     BIGINT UNSIGNED NOT NULL COMMENT '分类ID',
  created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (article_id, category_id),
  CONSTRAINT fk_article_category_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE,
  CONSTRAINT fk_article_category_category FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章-分类关联';

CREATE TABLE IF NOT EXISTS article_tag (
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  tag_id          BIGINT UNSIGNED NOT NULL COMMENT '标签ID',
  created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (article_id, tag_id),
  CONSTRAINT fk_article_tag_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE,
  CONSTRAINT fk_article_tag_tag FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章-标签关联';

-- =====================================================================
-- Table: article_stats / like / favorite / view
-- =====================================================================
CREATE TABLE IF NOT EXISTS article_stats (
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  views           BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '阅读量',
  likes           BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞数',
  comments        BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论数',
  favorites       BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '收藏数',
  last_view_at    DATETIME        NULL     COMMENT '最后阅读时间',
  PRIMARY KEY (article_id),
  CONSTRAINT fk_article_stats_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章统计汇总';

CREATE TABLE IF NOT EXISTS article_like (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '点赞记录ID',
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  user_id         CHAR(32)        NOT NULL COMMENT '用户ID',
  liked_at        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_article_like (article_id, user_id),
  KEY idx_like_user (user_id),
  CONSTRAINT fk_article_like_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章点赞记录';

CREATE TABLE IF NOT EXISTS article_favorite (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '收藏记录ID',
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  user_id         CHAR(32)        NOT NULL COMMENT '用户ID',
  favorited_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_article_favorite (article_id, user_id),
  KEY idx_fav_user (user_id),
  CONSTRAINT fk_article_favorite_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章收藏记录';

CREATE TABLE IF NOT EXISTS article_log (
  id              VARCHAR(32)     NOT NULL COMMENT '文章操作日志ID',
  `_class`        VARCHAR(255)    NULL     COMMENT '事件类',
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  user_id         CHAR(32)        NULL     COMMENT '用户ID（匿名为NULL）',
  action          VARCHAR(64)     NULL     COMMENT '动作',
  ip              VARCHAR(64)     NULL     COMMENT 'IP地址',
  ua              VARCHAR(512)    NULL     COMMENT 'User-Agent',
  referer         VARCHAR(512)    NULL     COMMENT '来源页面',
  view_at         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发生时间',
  PRIMARY KEY (id),
  KEY idx_article_log_article_time (article_id, view_at),
  KEY idx_article_log_user_article (user_id, article_id),
  CONSTRAINT fk_article_log_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章操作日志（可分区）';

-- =====================================================================
-- Table: comment / comment_reply / comment_reaction
-- =====================================================================
CREATE TABLE IF NOT EXISTS comment (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评论ID（楼主）',
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  user_id         CHAR(32)        NOT NULL COMMENT '评论用户ID',
  content         TEXT            NOT NULL COMMENT '评论内容',
  floor           INT             NOT NULL COMMENT '楼层序号',
  is_pinned       TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '是否置顶',
  has_reply       TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '是否有回复',
  comment_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at      DATETIME        NULL     COMMENT '软删除时间',
  PRIMARY KEY (id),
  KEY idx_comment_article_time (article_id, comment_at),
  KEY idx_comment_user (user_id),
  CONSTRAINT fk_comment_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章评论（楼主）';

CREATE TABLE IF NOT EXISTS comment_reply (
  id                  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '回复ID',
  article_id          CHAR(32)        NOT NULL COMMENT '文章ID（冗余便于按文章聚合）',
  comment_id          BIGINT UNSIGNED NOT NULL COMMENT '所属楼主评论ID',
  reply_to_reply_id   BIGINT UNSIGNED NULL     COMMENT '被回复的回复ID（回复楼主时为NULL）',
  user_id             CHAR(32)        NOT NULL COMMENT '回复者用户ID',
  reply_to_user_id    CHAR(32)        NULL     COMMENT '被回复者用户ID',
  content             TEXT            NOT NULL COMMENT '回复内容',
  reply_at            DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  created_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_reply_comment_time (comment_id, reply_at),
  KEY idx_reply_reply_to (reply_to_reply_id),
  KEY idx_reply_article (article_id),
  CONSTRAINT fk_reply_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE,
  CONSTRAINT fk_reply_comment FOREIGN KEY (comment_id) REFERENCES comment (id) ON DELETE CASCADE,
  CONSTRAINT fk_reply_to_reply FOREIGN KEY (reply_to_reply_id) REFERENCES comment_reply (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论回复（两层：回复楼主或回复回复者）';

CREATE TABLE IF NOT EXISTS comment_reaction (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评论/回复点赞与踩ID',
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID（冗余）',
  comment_id      BIGINT UNSIGNED NULL     COMMENT '评论ID（目标为楼主时填写）',
  reply_id        BIGINT UNSIGNED NULL     COMMENT '回复ID（目标为回复时填写）',
  user_id         CHAR(32)        NOT NULL COMMENT '操作用户ID',
  reaction_type   ENUM('like','dislike') NOT NULL COMMENT '反应类型：点赞或踩',
  react_at        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_reaction_comment (comment_id, user_id),
  UNIQUE KEY uk_reaction_reply (reply_id, user_id),
  KEY idx_reaction_user (user_id),
  CONSTRAINT fk_reaction_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE,
  CONSTRAINT fk_reaction_comment FOREIGN KEY (comment_id) REFERENCES comment (id) ON DELETE CASCADE,
  CONSTRAINT fk_reaction_reply FOREIGN KEY (reply_id) REFERENCES comment_reply (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论/回复的点赞与踩';

-- =====================================================================
-- Table: article_series / article_series_item（扩展：文章系列）
-- =====================================================================
CREATE TABLE IF NOT EXISTS article_series (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '系列ID',
  name            VARCHAR(255)    NOT NULL COMMENT '系列名称',
  description     VARCHAR(512)    NULL     COMMENT '系列描述',
  code            VARCHAR(128)    NOT NULL COMMENT '系列唯一编码',
  created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_series_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章系列';

CREATE TABLE IF NOT EXISTS article_series_item (
  series_id       BIGINT UNSIGNED NOT NULL COMMENT '系列ID',
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  sort_order      INT             NOT NULL DEFAULT 0 COMMENT '系列内排序',
  PRIMARY KEY (series_id, article_id),
  KEY idx_series_sort (series_id, sort_order),
  CONSTRAINT fk_series_item_series FOREIGN KEY (series_id) REFERENCES article_series (id) ON DELETE CASCADE,
  CONSTRAINT fk_series_item_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章系列项';

-- =====================================================================
-- Table: article_attachment（扩展：附件/资源）
-- =====================================================================
CREATE TABLE IF NOT EXISTS article_attachment (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '附件ID',
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  file_url        VARCHAR(512)    NOT NULL COMMENT '文件URL',
  file_type       VARCHAR(64)     NULL     COMMENT '文件类型（图片/文档等）',
  size            BIGINT UNSIGNED NULL     COMMENT '文件大小（字节）',
  hash            VARCHAR(128)    NULL     COMMENT '文件哈希',
  uploaded_at     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  PRIMARY KEY (id),
  KEY idx_attach_article (article_id),
  CONSTRAINT fk_attach_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章附件/资源';

-- =====================================================================
-- Table: article_revision（扩展：版本/草稿）
-- =====================================================================
CREATE TABLE IF NOT EXISTS article_revision (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '修订ID',
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  editor_version  VARCHAR(64)     NULL     COMMENT '编辑器版本',
  content_snapshot LONGTEXT       NULL     COMMENT '内容快照（整篇或章节拼接）',
  diff_json       JSON            NULL     COMMENT '差异JSON',
  saved_at        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '保存时间',
  saved_by        CHAR(32)        NULL     COMMENT '保存人',
  PRIMARY KEY (id),
  KEY idx_revision_article_time (article_id, saved_at),
  CONSTRAINT fk_revision_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章修订/草稿';

-- =====================================================================
-- Table: moderation_log（扩展：审核日志）
-- =====================================================================
CREATE TABLE IF NOT EXISTS moderation_log (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '审核日志ID',
  article_id      CHAR(32)        NOT NULL COMMENT '文章ID',
  action          VARCHAR(64)     NOT NULL COMMENT '动作：提交/审核通过/拒绝等',
  reason          VARCHAR(512)    NULL     COMMENT '原因',
  operator_id     CHAR(32)        NULL     COMMENT '操作人ID',
  acted_at        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (id),
  KEY idx_mod_article_time (article_id, acted_at),
  CONSTRAINT fk_mod_article FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章审核日志';

-- =====================================================================
-- 初始化数据
-- =====================================================================

-- 1. 分类
INSERT IGNORE INTO category (name, code, parent_id, path) VALUES
  ('技术', 'tech', NULL, 'tech'),
  ('后端', 'backend', 1, 'tech/backend'),
  ('前端', 'frontend', 1, 'tech/frontend'),
  ('生活', 'life', NULL, 'life'),
  ('随笔', 'essay', 4, 'life/essay');

-- 2. 标签
INSERT IGNORE INTO tag (name, code) VALUES
  ('Java', 'java'),
  ('Spring Boot', 'spring-boot'),
  ('MySQL', 'mysql'),
  ('Redis', 'redis'),
  ('Vue.js', 'vuejs'),
  ('React', 'react'),
  ('心情', 'mood'),
  ('旅行', 'travel');

-- 3. 文章 (假设作者ID为 'admin-uuid')
-- 文章1: 已发布的技术文章
INSERT IGNORE INTO article (id, title, author_id, status, summary, visibility, is_top, slug, cover_url, language, allow_comment, is_reviewed, review_status, publish_at, created_at) VALUES
  ('art-001', '深入理解Spring Boot自动配置', 'admin-uuid', 'published', '本文深入剖析Spring Boot自动配置原理...', 'public', 1, 'deep-dive-spring-boot-autoconfig', 'https://example.com/covers/springboot.jpg', 'zh_CN', 1, 1, 'approved', NOW(), NOW());

-- 文章2: 发布的随笔
INSERT IGNORE INTO article (id, title, author_id, status, summary, visibility, is_top, slug, cover_url, language, allow_comment, is_reviewed, review_status, publish_at, created_at) VALUES
  ('art-002', '2024年终总结', 'admin-uuid', 'published', '回顾过去一年...', 'public', 0, '2024-summary', 'https://example.com/covers/2024.jpg', 'zh_CN', 1, 1, 'approved', NOW(), NOW());

-- 文章3: 草稿
INSERT IGNORE INTO article (id, title, author_id, status, summary, visibility, is_top, slug, cover_url, language, allow_comment, is_reviewed, review_status, publish_at, created_at) VALUES
  ('art-003', 'Redis缓存实战指南', 'admin-uuid', 'draft', 'Redis实战...', 'private', 0, 'redis-in-action', NULL, 'zh_CN', 1, 0, 'pending', NULL, NOW());

-- 4. 文章关联分类
INSERT IGNORE INTO article_category (article_id, category_id) VALUES
  ('art-001', 2), -- 后端
  ('art-002', 5); -- 随笔

-- 5. 文章关联标签
INSERT IGNORE INTO article_tag (article_id, tag_id) VALUES
  ('art-001', 1), -- Java
  ('art-001', 2), -- Spring Boot
  ('art-002', 7); -- 心情

-- 6. 文章统计初始化
INSERT IGNORE INTO article_stats (article_id, views, likes, comments, favorites) VALUES
  ('art-001', 1205, 56, 3, 12),
  ('art-002', 340, 12, 1, 0),
  ('art-003', 0, 0, 0, 0);

-- 7. 文章章节 (art-001)
INSERT IGNORE INTO article_chapter (id, article_id, chapter_order, level, title, anchor, parent_id, path, start_offset, end_offset) VALUES
  (1, 'art-001', 1, 1, '引言', 'introduction', NULL, '1', 0, 100),
  (2, 'art-001', 2, 1, '核心原理', 'core-principle', NULL, '2', 101, 500),
  (3, 'art-001', 3, 2, '@EnableAutoConfiguration详解', 'annotation-detail', 2, '2/3', 501, 1200);

-- 8. 文章内容
INSERT IGNORE INTO article_content (article_id, content_md, content_html, words_count) VALUES
  ('art-001', '## 引言\nSpring Boot极大地简化了...', '<h2>引言</h2><p>Spring Boot极大地简化了...</p>', 2150);

-- 9. 评论数据 (假设用户ID 'user-001', 'user-002')
-- 楼主评论
INSERT IGNORE INTO comment (id, article_id, user_id, content, floor, is_pinned, has_reply, comment_at) VALUES
  (1, 'art-001', 'user-001', '写得非常好，通俗易懂！', 1, 0, 0, NOW()),
  (2, 'art-001', 'user-002', '有一个问题想请教...', 2, 0, 1, NOW());

-- 回复评论
INSERT IGNORE INTO comment_reply (article_id, comment_id, reply_to_reply_id, user_id, reply_to_user_id, content, reply_at) VALUES
  ('art-001', 2, NULL, 'admin-uuid', 'user-002', '请说，知无不言。', NOW());

-- 10. 评论点赞
INSERT IGNORE INTO comment_reaction (article_id, comment_id, user_id, reaction_type) VALUES
  ('art-001', 1, 'admin-uuid', 'like'),
  ('art-001', 1, 'user-002', 'like');

SET FOREIGN_KEY_CHECKS = 1;
