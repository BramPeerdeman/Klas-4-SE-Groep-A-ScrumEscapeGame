//package org.ScrumEscapeGame.ItemDataBase;
//
//import org.ScrumEscapeGame.Items.Rarity;
//import java.util.*;
//
//public class Database {
//    private static final Map<String, List<String>> data = new HashMap<>();
//
//    static {
//        // COMMON
//        data.put("Sprint|COMMON", List.of(
//                "A fixed period in which a selection from the backlog is developed into a working result."
//        ));
//        data.put("Product Backlog|COMMON", List.of(
//                "An ordered list containing all known features, fixes, and requirements for the product."
//        ));
//        data.put("Scrum Team|COMMON", List.of(
//                "A team consisting of a Product Owner, Scrum Master, and Developers working towards a product goal."
//        ));
//
//        // UNCOMMON
//        data.put("Sprint Review|UNCOMMON", List.of(
//                "At the end of the Sprint, the increment is presented to stakeholders to gather feedback."
//        ));
//        data.put("Product Owner|UNCOMMON", List.of(
//                "Responsible for managing the Product Backlog and maximizing the value of the product."
//        ));
//
//        // RARE
//        data.put("Sprint Retrospective|RARE", List.of(
//                "The full Scrum Team reflects on the past Sprint, discusses process bottlenecks, and agrees on actionable improvements. The Scrum Master facilitates, and the Product Owner shares feedback impact."
//        ));
//    }
//
//    public static List<String> getTexts(String subject, Rarity rarity) {
//        return data.getOrDefault(subject + "|" + rarity.name(), List.of("No information available."));
//    }
//}
