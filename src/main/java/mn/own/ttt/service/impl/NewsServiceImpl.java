package mn.own.ttt.service.impl;

import java.util.Optional;
import mn.own.ttt.domain.News;
import mn.own.ttt.repository.NewsRepository;
import mn.own.ttt.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link News}.
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    private final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public News save(News news) {
        log.debug("Request to save News : {}", news);
        return newsRepository.save(news);
    }

    @Override
    public Optional<News> partialUpdate(News news) {
        log.debug("Request to partially update News : {}", news);

        return newsRepository
            .findById(news.getId())
            .map(existingNews -> {
                if (news.getTitle() != null) {
                    existingNews.setTitle(news.getTitle());
                }
                if (news.getContent() != null) {
                    existingNews.setContent(news.getContent());
                }
                if (news.getCreatedBy() != null) {
                    existingNews.setCreatedBy(news.getCreatedBy());
                }
                if (news.getCreatedDate() != null) {
                    existingNews.setCreatedDate(news.getCreatedDate());
                }
                if (news.getLastModifiedBy() != null) {
                    existingNews.setLastModifiedBy(news.getLastModifiedBy());
                }
                if (news.getLastModifiedDate() != null) {
                    existingNews.setLastModifiedDate(news.getLastModifiedDate());
                }

                return existingNews;
            })
            .map(newsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<News> findAll(Pageable pageable) {
        log.debug("Request to get all News");
        return newsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<News> findOne(Long id) {
        log.debug("Request to get News : {}", id);
        return newsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete News : {}", id);
        newsRepository.deleteById(id);
    }
}
