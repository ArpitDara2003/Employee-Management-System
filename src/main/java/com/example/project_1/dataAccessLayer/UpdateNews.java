package com.example.project_1.dataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.example.project_1.dataModels.News;



public class UpdateNews {

    public static News[] getNewsFromDB(Session session, Long companyId) {
        List<News> newsList = null;

        try {
            Query<News> query = session.createQuery("FROM lu_news WHERE fk_company_id = :companyId", News.class);
            query.setParameter("companyId", companyId);
            newsList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsList != null ? newsList.toArray(new News[0]) : new News[0];
    }

    public static void updateNews(Long newsId, News news) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourDatabase", "username", "password")) {
            String query = "UPDATE lu_news SET heading = ?, description = ?, image_url = ?, posted_time = ?, company_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, news.getHeading());
            preparedStatement.setString(2, news.getDescription());
            preparedStatement.setString(3, news.getImageUrl());
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(news.getPostedTime()));
            preparedStatement.setLong(5, news.getCompanyId());
            preparedStatement.setLong(6, newsId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
