package com.lx.blog.repository.listener;

import com.lx.blog.common.aop.log.OpLogEvent;
import com.lx.blog.repository.dao.ArticleStatsDao;
import com.lx.blog.repository.dao.ArticleViewDao;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleView;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章阅读日志事件监听器
 */
@Component
@RequiredArgsConstructor
public class ArticleOpLogEventListener {

    @NotNull private final ArticleViewDao viewDao;
    @NotNull private final ArticleStatsDao statsDao;

    /**
     * 处理文章阅读日志事件
     *
     * @param event 文章阅读日志事件
     */
    @EventListener
    public void onOpLog(OpLogEvent event) {
        if (!"article_view".equals(event.getTableName())) return;
        Map<String, Object> p = event.getPayload();
        String articleId = (String) p.get("article_id");
        String ip = (String) p.get("ip");
        String ua = (String) p.get("user_agent");
        LocalDateTime at = (LocalDateTime) p.get("logged_at");
        ArticleView v = ArticleView.builder().articleId(articleId).ip(ip).ua(ua).viewAt(at).build();
        viewDao.appendView(v);
        statsDao.incViews(articleId, 1);
    }
}

