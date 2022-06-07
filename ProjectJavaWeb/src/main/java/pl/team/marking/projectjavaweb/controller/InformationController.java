package pl.team.marking.projectjavaweb.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.team.marking.projectjavaweb.DTO.InformationDTO;
import pl.team.marking.projectjavaweb.DTO.PublishedUserDTO;
import pl.team.marking.projectjavaweb.entity.Category;
import pl.team.marking.projectjavaweb.entity.Information;
import pl.team.marking.projectjavaweb.entity.MyUserDetails;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.repository.CategoryRepository;
import pl.team.marking.projectjavaweb.repository.InformationRepository;
import pl.team.marking.projectjavaweb.repository.UserRepository;
import pl.team.marking.projectjavaweb.service.MyUserDetailsService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
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
    private final UserRepository userRepository;

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
    public String createInformation(@AuthenticationPrincipal MyUserDetails userDetails, @Valid @ModelAttribute("informationDTO") InformationDTO informationDTO) {
        Information information = new Information();

        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);
        information.setUser(user);

        Optional<Category> category = categoryRepository.findById(informationDTO.getCategoryId());
        if (category.isEmpty())
            return "redirect:/informations";
        information.setCategory(category.get());

        information.setTitle(informationDTO.getTitle());
        information.setContent(informationDTO.getContent());

        information.setRemindDate(informationDTO.getRemindDate());

        String uuid = String.valueOf(UUID.randomUUID());
        information.setLink(BASIC_URL + uuid);
        information.setLinkUuid(uuid);

        informationRepository.save(information);
        return "redirect:/informations";
    }

    @GetMapping("/update/{informationId}")
    public String shareFormInformation(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long informationId, Model model) {
        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);

        Optional<Information> information = informationRepository.findByInformationIdAndUser(informationId, user);
        if (information.isEmpty())
            return "redirect:/informations";

        InformationDTO informationDTO = new InformationDTO();
        informationDTO.setTitle(information.get().getTitle());
        informationDTO.setContent(information.get().getContent());
        informationDTO.setRemindDate(information.get().getRemindDate());

        model.addAttribute("information", information.get());
        model.addAttribute("informationDTO", informationDTO);
        model.addAttribute("publishedUser", new PublishedUserDTO());
        model.addAttribute("categories", categoryRepository.findByAllCategoryWithoutCategoryWithId(information.get().getCategory().getCategoryId()));
        return "information/information_edit";
    }

    @PostMapping("/update/{informationId}")
    public String updateInformation(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long informationId, @Valid @ModelAttribute("informationDTO") InformationDTO informationDTO) {
        String ownerLogin = userDetails.getUsername();
        UserApp owner = myUserDetailsService.getUserByLogin(ownerLogin);
        Optional<Information> information = informationRepository.findByInformationIdAndUser(informationId, owner);
        if (information.isEmpty())
            return "redirect:/informations";

        Information updateInformation = information.get();
        updateInformation.setTitle(informationDTO.getTitle());
        updateInformation.setContent(informationDTO.getContent());

        updateInformation.setRemindDate(informationDTO.getRemindDate());

        if (!Objects.equals(updateInformation.getCategory().getCategoryId(), informationDTO.getCategoryId())
                && informationDTO.getCategoryId() != null) {
            Optional<Category> category = categoryRepository.findById(informationDTO.getCategoryId());
            category.ifPresent(updateInformation::setCategory);
        }
        informationRepository.save(updateInformation);
        return "redirect:/informations/update/" + informationId;
    }

    @PostMapping("/{informationId}/share")
    public String shareInformation(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long informationId, @Valid @ModelAttribute("publishedUser") PublishedUserDTO publishedUserDTO) {
        String ownerLogin = userDetails.getUsername();
        UserApp owner = myUserDetailsService.getUserByLogin(ownerLogin);

        Optional<Information> information = informationRepository.findByInformationIdAndUser(informationId, owner);
        Optional<UserApp> user = userRepository.findUserByLogin(publishedUserDTO.getLogin());
        if (information.isEmpty() || user.isEmpty())
            return "redirect:/informations";

        Information newInformation = information.get();
        for (UserApp shareUser : newInformation.getPublishedUser()) {
            if (Objects.equals(shareUser.getLogin(), user.get().getLogin()))
                return "redirect:/informations/update/" + informationId;
        }

        newInformation.getPublishedUser().add(user.get());
        informationRepository.save(newInformation);
        return "redirect:/informations/update/" + informationId;
    }

    @PostMapping("/delete/{informationId}")
    public String deleteInformation(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long informationId) {
        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);

        Optional<Information> information = informationRepository.findByInformationIdAndUser(informationId, user);
        information.ifPresent(informationRepository::delete);
        return "redirect:/informations";
    }
}
