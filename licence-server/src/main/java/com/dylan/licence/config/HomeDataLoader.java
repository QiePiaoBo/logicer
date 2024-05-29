//package com.dylan.licence.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @Classname HomeDataLoader
// * @Description HomeDataLoader
// * @Date 8/29/2023 9:37 AM
// */
//@RefreshScope
//@Component
////@ConfigurationProperties(prefix = "logicer.data.home")
//public class HomeDataLoader {
//
//    private String title;
//
//    private List<String> favors;
//
//    private Map<String,String> proverbs;
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public List<String> getFavors() {
//        return favors;
//    }
//
//    public void setFavors(List<String> favors) {
//        this.favors = favors;
//    }
//
//    public Map<String, String> getProverbs() {
//        return proverbs;
//    }
//
//    public void setProverbs(Map<String, String> proverbs) {
//        this.proverbs = proverbs;
//    }
//}
