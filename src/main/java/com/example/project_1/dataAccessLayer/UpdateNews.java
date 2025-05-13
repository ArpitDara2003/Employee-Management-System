package com.example.project_1.dataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.example.project_1.dataModels.NewsItems;

public class UpdateNews {

    public static List<NewsItems> getNewsFromDB(Session session, Long companyId) {
        List<NewsItems> newsList = null;

        try {
            Query<NewsItems> query = session.createQuery(
                "SELECT n.heading, n.description, n.imageUrl, n.postedTime, n.company.companyId FROM NewsItems n WHERE n.company.companyId = :companyId",
                NewsItems.class
            );
            query.setParameter("companyId", companyId);
            newsList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsList != null ? newsList : List.of();
    }

    public static void updateNews(Long newsId, NewsItems news) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourDatabase", "username", "password")) {
            String query = "UPDATE lu_news SET heading = ?, description = ?, image_url = ?, posted_time = ?, company_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, news.getHeading());
            preparedStatement.setString(2, news.getDescription());
            preparedStatement.setString(3, news.getImageUrl());
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(news.getPostedTime()));
            preparedStatement.setLong(5, news.getCompany().getCompanyId());
            preparedStatement.setLong(6, newsId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
