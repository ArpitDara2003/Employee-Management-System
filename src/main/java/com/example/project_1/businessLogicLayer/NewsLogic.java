package com.example.project_1.businessLogicLayer;

import com.example.project_1.dataAccessLayer.UpdateNews;
import com.example.project_1.dataModels.NewsItems;
import com.example.project_1.utils.HibernateUtil;

import org.hibernate.Session;

import java.util.List;

public class NewsLogic {

    static Session session = HibernateUtil.getSession();
    
    public static List<NewsItems> getNews(Long companyId) {
        return UpdateNews.getNewsFromDB(session, companyId);
    }

    public static void updateNews(Long newsId, NewsItems news) {
        if (news.getHeading() == null || news.getDescription() == null) {
            throw new IllegalArgumentException("Invalid news details");
        }

        UpdateNews.updateNews(newsId, news);
    }
}