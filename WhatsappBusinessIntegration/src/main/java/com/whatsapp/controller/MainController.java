
package com.whatsapp.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whatsapp.entity.Appointment;
import com.whatsapp.entity.Lead;
import com.whatsapp.entity.Property;
import com.whatsapp.repositories.AppointmentRepository;
import com.whatsapp.repositories.PropertyRepository;
import com.whatsapp.service.ChatService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    private final ChatService chatService;
    private final PropertyRepository propertyRepository;
    private final AppointmentRepository appointmentRepository;

    public MainController(ChatService chatService,
                          PropertyRepository propertyRepository,
                          AppointmentRepository appointmentRepository) {
        this.chatService = chatService;
        this.propertyRepository = propertyRepository;
        this.appointmentRepository = appointmentRepository;
    }

    /* ================= HOME ================= */

    @GetMapping("/")
    public String home() {
        return "index";
    }

    

    /* ================= PROPERTIES ================= */

    @GetMapping("/properties")
    public String properties(Model model) {
        model.addAttribute("properties", propertyRepository.findAll());
        model.addAttribute("property", new Property());
        return "properties";
    }

    @PostMapping("/properties/save")
    public String saveProperty(@ModelAttribute Property property) {
        propertyRepository.save(property);
        return "redirect:/properties";
    }

    @GetMapping("/properties/edit/{id}")
    public String editProperty(@PathVariable Long id, Model model) {
        model.addAttribute("properties", propertyRepository.findAll());
        model.addAttribute("property",
                propertyRepository.findById(id).orElseThrow());
        return "properties";
    }

    @GetMapping("/properties/delete/{id}")
    public String deleteProperty(@PathVariable Long id) {
        propertyRepository.deleteById(id);
        return "redirect:/properties";
    }

    /* ================= APPOINTMENTS ================= */

    @GetMapping("/appointments")
    public String appointments(Model model) {
        model.addAttribute("appointments", appointmentRepository.findAll());
        return "appointments";
    }

    
    /* ================= APPOINTMENT FROM CHAT ================= */

 
    @GetMapping("/appointments/new")
    public String newAppointment(Model model, HttpSession session) {

        Lead lead = (Lead) session.getAttribute("lead");
        if (lead == null) {
            return "redirect:/chat";
        }

        model.addAttribute("properties",
                propertyRepository.findAll());

        return "appointments";
    }



 // SAVE APPOINTMENT
// @PostMapping("/appointments/save")
// public String saveAppointment(
//         @RequestParam Long propertyId,
//         @RequestParam String visitDate,
//         @RequestParam String visitTime,
//         HttpSession session) {
//
//     Lead lead = (Lead) session.getAttribute("lead");
//     Property property = propertyRepository.findById(propertyId).orElseThrow();
//
//     Appointment appointment = new Appointment();
//     appointment.setLead(lead);
//     appointment.setProperty(property);
//     appointment.setVisitDate(LocalDate.parse(visitDate));
//     appointment.setVisitTime(LocalTime.parse(visitTime));
//     appointment.setStatus("SCHEDULED");
//
//     appointmentRepository.save(appointment);
//
//     return "redirect:/appointments";
// }
    @PostMapping("/appointments/save")
    public String saveAppointment(
            @RequestParam(required = false) Long propertyId,
            @RequestParam String visitDate,
            @RequestParam String visitTime,
            HttpSession session) {

        Lead lead = (Lead) session.getAttribute("lead");

        // ✅ Session safety
        if (lead == null) {
            return "redirect:/chat";
        }

        Property property = null;

        // ✅ If propertyId comes from UI
        if (propertyId != null) {
            property = propertyRepository.findById(propertyId).orElse(null);
        }

        // ✅ If propertyId NOT provided (chat flow)
        if (property == null) {
            property = propertyRepository
                    .findByCityIgnoreCaseAndAvailable(
                            lead.getPreferredLocation(), true)
                    .stream()
                    .filter(p -> p.getPrice() <= lead.getBudget())
                    .findFirst()
                    .orElse(null);
        }

        // ✅ Final validation
        if (property == null) {
            return "redirect:/chat";
        }

        Appointment appointment = new Appointment();
        appointment.setLead(lead);
        appointment.setProperty(property);

        try {
            appointment.setVisitDate(LocalDate.parse(visitDate));
            appointment.setVisitTime(LocalTime.parse(visitTime));
        } catch (Exception e) {
            return "redirect:/appointments/new";
        }

        appointment.setStatus("SCHEDULED");
        appointmentRepository.save(appointment);

        String successMsg = chatService.appointmentSuccessMessage(
                lead.getLanguage(),
                visitDate,
                visitTime
        );

        session.setAttribute("appointmentSuccess", successMsg);


        // ✅ GO BACK TO CHAT
        return "redirect:/chat";
    }


}

