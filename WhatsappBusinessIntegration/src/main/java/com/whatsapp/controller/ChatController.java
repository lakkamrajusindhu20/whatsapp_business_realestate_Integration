
package com.whatsapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whatsapp.entity.Lead;
import com.whatsapp.repositories.LeadRepository;
import com.whatsapp.service.ChatService;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final LeadRepository leadRepository;
    private final ChatService chatService;

    public ChatController(LeadRepository leadRepository,
                          ChatService chatService) {
        this.leadRepository = leadRepository;
        this.chatService = chatService;
    }

    @GetMapping
    public String chatPage() {
        return "chat";
    }

    @PostMapping("/send")
    public String send(
            @RequestParam String phoneNumber,
            @RequestParam(required = false) String language,
            @RequestParam String message,
            HttpSession session,
            Model model) {

        Lead lead = (Lead) session.getAttribute("lead");

        if (lead == null) {
            lead = leadRepository
                    .findByPhoneNumber(phoneNumber)
                    .orElseGet(() -> {
                        Lead l = new Lead();
                        l.setPhoneNumber(phoneNumber);
                        l.setLanguage(language);
                        l.setStatus("NEW");
                        return leadRepository.save(l);
                    });

            session.setAttribute("lead", lead);
        }

        // ✅ CHAT LOGIC
        String response = chatService.handleMessage(
                lead.getPhoneNumber(),
                lead.getLanguage(),
                message
        );

        // ✅ REFRESH LEAD FROM DB (THIS WAS THE BUG)
        lead = leadRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        session.setAttribute("lead", lead);

        model.addAttribute("response", response);

        return "chat";
    }

}
