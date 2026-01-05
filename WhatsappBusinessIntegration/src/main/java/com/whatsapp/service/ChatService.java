package com.whatsapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.whatsapp.entity.ConversationMessage;
import com.whatsapp.entity.Lead;
import com.whatsapp.entity.Property;
import com.whatsapp.repositories.ConversationMessageRepository;
import com.whatsapp.repositories.LeadRepository;
import com.whatsapp.repositories.PropertyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final LeadRepository leadRepository;
    private final PropertyRepository propertyRepository;
    private final ConversationMessageRepository messageRepository;

    public String handleMessage(String phoneNumber, String language, String message) {

        final String lang = (language == null || language.isBlank())
                ? "EN"
                : language.trim().toUpperCase();

        Lead lead = leadRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> {
                    Lead l = new Lead();
                    l.setPhoneNumber(phoneNumber);
                    l.setLanguage(lang);
                    l.setStatus("NEW");
                    return leadRepository.save(l);
                });

        saveMessage(lead, "USER", message);

        /* ================= 1️⃣ WELCOME ================= */

        if ("NEW".equals(lead.getStatus())) {
            lead.setStatus("INTENT_PENDING");
            leadRepository.save(lead);
            return welcomeMessage(lang);
        }

        /* ================= 2️⃣ BUY / RENT ================= */

        if ("INTENT_PENDING".equals(lead.getStatus())) {

            if (isBuyIntent(message) || isRentIntent(message)) {
                lead.setStatus("CITY_PENDING");
                leadRepository.save(lead);
                return askCity(lang);
            }

            return invalidOption(lang);
        }

        /* ================= 3️⃣ CITY ================= */

        if ("CITY_PENDING".equals(lead.getStatus())) {
            lead.setPreferredLocation(message);
            lead.setStatus("BUDGET_PENDING");
            leadRepository.save(lead);
            return askBudget(lang);
        }

        /* ================= 4️⃣ BUDGET ================= */

        if ("BUDGET_PENDING".equals(lead.getStatus())) {
            try {
                lead.setBudget(Double.parseDouble(message));
                lead.setStatus("PROPERTIES_SHOWN");
                leadRepository.save(lead);
                return showProperties(lead, lang);
            } catch (Exception e) {
                return invalidBudget(lang);
            }
        }

        /* ================= 5️⃣ BOOK ================= */

        if ("PROPERTIES_SHOWN".equals(lead.getStatus())
                && message.toLowerCase().startsWith("book")) {

            return scheduleInstruction(lang);
        }

        return thankYou(lang);
    }

    /* ================= HELPERS ================= */

    private void saveMessage(Lead lead, String sender, String text) {
        ConversationMessage msg = new ConversationMessage();
        msg.setLead(lead);
        msg.setSender(sender);
        msg.setMessage(text);
        msg.setTimestamp(LocalDateTime.now());
        messageRepository.save(msg);
    }

    private boolean isBuyIntent(String msg) {
        msg = msg.trim().toLowerCase();
        return msg.equals("1") || msg.equals("buy")
                || msg.equals("खरीदें")
                || msg.equals("కొనండి");
    }

    private boolean isRentIntent(String msg) {
        msg = msg.trim().toLowerCase();
        return msg.equals("2") || msg.equals("rent")
                || msg.equals("किराया")
                || msg.equals("అద్దె");
    }

    private String showProperties(Lead lead, String lang) {

        List<Property> properties =
                propertyRepository.findByCityIgnoreCaseAndAvailable(
                        lead.getPreferredLocation(), true);

        if (properties.isEmpty()) {
            return noProperty(lang);
        }

        StringBuilder sb = new StringBuilder();

     
     sb.append("<b>")
       .append(propertyIntro(lang))
       .append("</b><br/><br/>");

     int i = 1;

     for (Property p : properties) {
         if (p.getPrice() <= lead.getBudget()) {

             sb.append("<div style='border:1px solid #ccc;padding:10px;margin-bottom:10px;'>");

             sb.append("<b>")
               .append(i++)
               .append(". ")
               .append(p.getTitle())
               .append("</b><br/>");

             sb.append("📍 ")
               .append(p.getCity())
               .append("<br/>");

             sb.append("💰 ₹")
               .append(p.getPrice())
               .append("<br/>");

             if (p.getImageUrl() != null && !p.getImageUrl().trim().isEmpty()) {
                 sb.append("<img src='")
                   .append(p.getImageUrl())
                   .append("' style='width:200px;height:auto;margin-top:5px;'/>");
             }

             sb.append("</div>");
         }
     }

     if (i == 1) {
         return lang.equals("HI") ? "❌ आपके बजट में कोई प्रॉपर्टी उपलब्ध नहीं है"
              : lang.equals("TE") ? "❌ మీ బడ్జెట్‌లో ప్రాపర్టీలు లేవు"
              : "❌ No properties available in your budget";
     }

     return sb.toString();

    }

    /* ================= LANGUAGE ================= */

    private String welcomeMessage(String l) {
        return l.equals("HI") ? "रियल एस्टेट सेवा में आपका स्वागत है!\n1️⃣ खरीदें\n2️⃣ किराया"
             : l.equals("TE") ? "రియల్ ఎస్టేట్ సేవలకు స్వాగతం!\n1️⃣ కొనండి\n2️⃣ అద్దె"
             : "Welcome to Real Estate Services!\n1️⃣ Buy\n2️⃣ Rent";
    }

    private String askCity(String l) {
        return l.equals("HI") ? "किस शहर में प्रॉपर्टी चाहिए?"
             : l.equals("TE") ? "ఏ నగరంలో కావాలి?"
             : "Which city are you looking for?";
    }

    private String askBudget(String l) {
        return l.equals("HI") ? "आपका बजट क्या है?"
             : l.equals("TE") ? "మీ బడ్జెట్ ఎంత?"
             : "What is your budget?";
    }

    private String invalidOption(String l) {
        return l.equals("HI") ? "कृपया सही विकल्प चुनें"
             : l.equals("TE") ? "సరైన ఎంపిక ఇవ్వండి"
             : "Please choose a valid option";
    }

    private String invalidBudget(String l) {
        return l.equals("HI") ? "सही बजट दें"
             : l.equals("TE") ? "సరైన బడ్జెట్ ఇవ్వండి"
             : "Enter a valid budget";
    }

    private String noProperty(String l) {
        return l.equals("HI") ? "क्षमा करें, इस बजट में प्रॉपर्टी उपलब्ध नहीं है"
             : l.equals("TE") ? "ఈ బడ్జెట్‌లో ప్రాపర్టీలు లేవు"
             : "Sorry, no properties available in your budget";
    }

    private String propertyHeader(String l) {
        return l.equals("HI") ? "🏠 उपलब्ध प्रॉपर्टी:"
             : l.equals("TE") ? "🏠 లభ్యమైన ప్రాపర్టీలు:"
             : "🏠 Available Properties:";
    }

    private String bookInstruction(String l) {
        return l.equals("HI") ? "बुक करने के लिए: Book लिखें"
             : l.equals("TE") ? "బుక్ చేయడానికి: Book టైప్ చేయండి"
             : "Type: Book to schedule a visit";
    }

    private String scheduleInstruction(String l) {
        return l.equals("HI") ? "SCHEDULE_APPOINTMENT"
             : l.equals("TE") ? "SCHEDULE_APPOINTMENT"
             : "SCHEDULE_APPOINTMENT";
    }

    private String thankYou(String l) {
        return l.equals("HI") ? "धन्यवाद!"
             : l.equals("TE") ? "ధన్యవాదాలు!"
             : "Thank you!";
    }
    private String propertyIntro(String lang) {
        switch (lang) {
            case "HI":
                return "🏠 आपके बजट में उपलब्ध प्रॉपर्टी:";
            case "TE":
                return "🏠 మీ బడ్జెట్‌లో లభ్యమైన ప్రాపర్టీలు:";
            default:
                return "🏠 Available properties in your budget:";
        }
    }
    public String appointmentSuccessMessage(String lang,
            String date,
            String time) {

lang = (lang == null) ? "EN" : lang.toUpperCase();

return lang.equals("HI")
? "✅ आपका अपॉइंटमेंट सफलतापूर्वक बुक हो गया है।\n"
+ "हमारा एजेंट " + date + " को " + time + " बजे मिलेगा।"

: lang.equals("TE")
? "✅ మీ అపాయింట్‌మెంట్ విజయవంతంగా షెడ్యూల్ అయింది.\n"
+ date + " న " + time + " కి మా ఏజెంట్ కలుస్తారు."

: "✅ Your appointment is successfully scheduled.\n"
+ "Our agent will meet you on " + date + " at " + time + ".";
}


   
}
