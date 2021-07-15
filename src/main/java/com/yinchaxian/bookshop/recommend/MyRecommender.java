package com.yinchaxian.bookshop.recommend;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyRecommender {
    public static List<Long> getRecommendForUser(int userId) throws Exception {
        //准备数据 这里是电影评分数据
        File file = new File("/home/yinchaxian/rate.dat");
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        FileDataModel dataModel = new FileDataModel(file);
        //计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        //计算最近邻域，邻居有两种算法，基于固定数量的邻居和基于相似度的邻居，这里使用基于固定数量的邻居
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
        //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于用户的协同过滤推荐
        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
        //给用户ID等于5的用户推荐10部电影
        List<RecommendedItem> itemList = recommender.recommend(userId, 10);
        List<Long> list = new ArrayList<>();
        //打印推荐的结果
        System.out.println("使用基于用户的协同过滤算法");
        System.out.println("为用户" + userId + "推荐10个商品");
        for (RecommendedItem item : itemList) {
            System.out.println(item.toString());
            list.add(item.getItemID());
        }

        return list;
    }

    public static List<Long> getRecommendForItem(int userId, long bookId) throws Exception {
        //准备数据 这里是电影评分数据
        File file = new File("/home/yinchaxian/rate.dat");
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        FileDataModel dataModel = new FileDataModel(file);
        //计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于物品的协同过滤推荐
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
        //给用户ID等于5的用户推荐10个与2398相似的商品
        List<RecommendedItem> itemList = recommender.recommendedBecause(userId, bookId, 10);
        List<Long> list = new ArrayList<>();
        //打印推荐的结果
        System.out.println("使用基于物品的协同过滤算法");
        System.out.println("为用户" + userId + "推荐10个相似的商品");
        for (RecommendedItem item : itemList) {
            System.out.println(item);
            list.add(item.getItemID());
        }
        return list;
    }
}