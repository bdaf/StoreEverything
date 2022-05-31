package pl.team.marking.projectjavaweb.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.team.marking.projectjavaweb.DTO.InformationDTO;
import pl.team.marking.projectjavaweb.entity.Category;
import pl.team.marking.projectjavaweb.entity.Information;
import pl.team.marking.projectjavaweb.entity.MyUserDetails;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.repository.CategoryRepository;
import pl.team.marking.projectjavaweb.repository.InformationRepository;
import pl.team.marking.projectjavaweb.service.MyUserDetailsService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/informations")
public class InformationController {

    private final String BASIC_URL = "http://localhost:8088/informations/share/link/";

    private final MyUserDetailsService myUserDetailsService;

    private final InformationRepository informationRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String getAllMyInformation(@AuthenticationPrincipal MyUserDetails userDetails, Model model){
        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);

        List<Information> informationList = informationRepository.findByUser(user);
        model.addAttribute("informations", informationList);
        model.addAttribute("isShare", false);
        return "information/informations";
    }

    @GetMapping("/{informationId}")
    public String getInformationDetails(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long informationId, Model model){
        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);

        Optional<Information> information = informationRepository.findByInformationIdAndUser(informationId, user);
        if(information.isEmpty())
            return "redirect:/informations";
        model.addAttribute("information", information.get());
        model.addAttribute("canEdited", true);
        return "information/information_details";
    }

    @GetMapping("/share")
    public String getShareInformation(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        String login = userDetails.getUsername();
        List<Information> informationList = informationRepository.findShareInformationForUser(login);
        model.addAttribute("informations", informationList);
        model.addAttribute("isShare", true);
        return "information/informations";
    }

    // TODO
    @GetMapping("informations/share/{informationId}")
    public String getShareInformationDetails(@AuthenticationPrincipal MyUserDetails userDetails, Model model, @PathVariable Long informationId) {
        String login = userDetails.getUsername();

        Optional<Information> information = informationRepository.findShareInformationForUser(informationId, login);
        if (information.isEmpty())
            return "redirect:/informations/share";
        model.addAttribute("information", information.get());
        model.addAttribute("canEdited", false);
        return "information/information_details";
    }

    @GetMapping("/share/link/{uuid}")
    public String getShareLinkInformation(Model model, @PathVariable String uuid) {
        Optional<Information> information = informationRepository.findByLinkUuid(uuid);
        if (information.isEmpty())
            return "redirect:/informations";
        model.addAttribute("information", information.get());
        model.addAttribute("canEdited", false);
        return "information/information_details";
    }

    @GetMapping("/add")
    public String createFormsInformation(Model model){
        model.addAttribute("informationDTO", new InformationDTO());
        model.addAttribute("categories", categoryRepository.findAll());
        return "information/information_add";
    }

    @PostMapping("/add")
    public String createInformation(@AuthenticationPrincipal MyUserDetails userDetails, @ModelAttribute("informationDTO") InformationDTO informationDTO){
        Information information = new Information();

        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);
        information.setUser(user);

        Optional<Category> category = categoryRepository.findById(informationDTO.getCategoryId());
        if(category.isEmpty())
            return "redirect:/informations";
        information.setCategory(category.get());

        information.setTitle(informationDTO.getTitle());
        information.setContent(informationDTO.getContent());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate remindDate = LocalDate.parse(informationDTO.getRemindDate(), formatter);
        information.setRemindDate(remindDate);
        String uuid = String.valueOf(UUID.randomUUID());
        information.setLink(BASIC_URL + uuid);
        information.setLinkUuid(uuid);

        informationRepository.save(information);
        return "redirect:/informations";
    }

    // TODO
    @PutMapping("/{informationId}")
    public String updateInformation(@PathVariable Long informationId, @ModelAttribute("information") Information information){
        return "redirect:/informations";
    }

    @PostMapping("/delete/{informationId}")
    public String deleteInformation(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long informationId){
        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);

        Optional<Information> information = informationRepository.findByInformationIdAndUser(informationId, user);
        information.ifPresent(informationRepository::delete);
        return "redirect:/informations";
    }
}
