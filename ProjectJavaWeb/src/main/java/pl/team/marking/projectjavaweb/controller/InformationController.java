package pl.team.marking.projectjavaweb.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    public String getAllMyInformation(@RequestParam(name = "by",required = false,defaultValue = "") String id, @RequestParam(name = "name",required = false,defaultValue = "") String name, @RequestParam(name = "date", required = false,defaultValue = "") String date, @AuthenticationPrincipal MyUserDetails userDetails, Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);
        List<Information> informationList = informationRepository.findByUser(user);
        if(!name.equals("")) {
         if(name.equals("MostPopular")){
            Map<String,Integer> most = new HashMap<>();
            for(Information i : informationList){
                if (!most.containsKey(i.getCategory().getName())){
                    most.put(i.getCategory().getName(), 1);
                }
                else {
                    int currentCount = most.get(i.getCategory().getName());
                    currentCount++;
                    most.put(i.getCategory().getName(), currentCount);
                }
            }
            String mostPopular = most.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
            informationList = informationRepository.findByUserAndCategoryName(user,mostPopular);
         }
            else
            informationList = informationList.stream().filter(i -> i.getCategory().getName().equals(name)).collect(Collectors.toList());
        }
            LocalDate currentDate = LocalDate.now();
        LocalDate nextDate;

        if(!date.equals("")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            nextDate = LocalDate.parse(date,formatter);
//            if(nextDate.isAfter(currentDate)) {
                informationList = informationList.stream().filter(i -> i.getAddingDate().toString().equals(nextDate.toString())).collect(Collectors.toList());
//            }
            }
        addCookie(id, response);
            List<Category> categoriesList = categoryRepository.findAll();
            if(!id.equals("")){
                if(id.equals("1"))
                    informationList = informationList.stream().sorted(Comparator.comparing(Information::getTitle)).collect(Collectors.toList());
                else if(id.equals("2"))
                    informationList = informationList.stream().sorted(Comparator.comparing(Information::getTitle).reversed()).collect(Collectors.toList());
                else if(id.equals("3"))
                    informationList = informationList.stream().sorted(Comparator.comparing(d -> d.getCategory().getName())).collect(Collectors.toList());
                else if(id.equals("4"))
                    informationList = informationList.stream().sorted(Comparator.comparing((Information d) -> d.getCategory().getName()).reversed()).collect(Collectors.toList());
                else if(id.equals("5"))
                    informationList = informationList.stream().sorted(Comparator.comparing(Information::getAddingDate).reversed()).collect(Collectors.toList());
                else if(id.equals("6"))
                    informationList = informationList.stream().sorted(Comparator.comparing(Information::getAddingDate)).collect(Collectors.toList());
            }
            else {
                for (Cookie c : request.getCookies()) {
                    if (c.getName().equals("sort") && c.getValue().equals("title") && name.equals("") && date.equals("")) {
                        informationList = informationList.stream().sorted(Comparator.comparing(Information::getTitle)).collect(Collectors.toList());
                    } else if (c.getName().equals("sort") && c.getValue().equals("titledesc") && name.equals("") && date.equals("")) {
                        informationList = informationList.stream().sorted(Comparator.comparing(Information::getTitle).reversed()).collect(Collectors.toList());
                    } else if (c.getName().equals("sort") && c.getValue().equals("category") && name.equals("") && date.equals("")) {
                        informationList = informationList.stream().sorted(Comparator.comparing(d -> d.getCategory().getName())).collect(Collectors.toList());
                    } else if (c.getName().equals("sort") && c.getValue().equals("categorydesc") && name.equals("") && date.equals("")) {
                        informationList = informationList.stream().sorted(Comparator.comparing((Information d) -> d.getCategory().getName()).reversed()).collect(Collectors.toList());
                    } else if (c.getName().equals("sort") && c.getValue().equals("date") && name.equals("") && date.equals("")) {
                        informationList = informationList.stream().sorted(Comparator.comparing(Information::getAddingDate)).collect(Collectors.toList());
                    } else if (c.getName().equals("sort") && c.getValue().equals("datedesc") && name.equals("") && date.equals("")) {
                        informationList = informationList.stream().sorted(Comparator.comparing(Information::getAddingDate).reversed()).collect(Collectors.toList());
                    }
                }
            }

        model.addAttribute("informations", informationList);
        model.addAttribute("categories",categoriesList);
        model.addAttribute("isShare", false);
        return "information/informations";
    }

    private void addCookie(String id, HttpServletResponse response) {
        if(id.equals("1")){
            Cookie cookie = new Cookie("sort", "title");
            cookie.setMaxAge(30 * 24 * 60 * 60);
            response.addCookie(cookie);
        }
        else if(id.equals("2")){
            Cookie cookie = new Cookie("sort", "titledesc");
            cookie.setMaxAge(30 * 24 * 60 * 60);
            response.addCookie(cookie);
        }
        else if(id.equals("3")){
            Cookie cookie = new Cookie("sort", "category");
            cookie.setMaxAge(30 * 24 * 60 * 60);
            response.addCookie(cookie);
        }
        else if(id.equals("4")){
            Cookie cookie = new Cookie("sort", "categorydesc");
            cookie.setMaxAge(30 * 24 * 60 * 60);
            response.addCookie(cookie);
        }
        else if(id.equals("5")){
            Cookie cookie = new Cookie("sort", "date");
            cookie.setMaxAge(30 * 24 * 60 * 60);
            response.addCookie(cookie);
        }
        else if(id.equals("6")){
            Cookie cookie = new Cookie("sort", "datedesc");
            cookie.setMaxAge(30 * 24 * 60 * 60);
            response.addCookie(cookie);
        }
    }

    @GetMapping("/remind")
    public String getAllMyRemindInformation(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);

        List<Information> informationList = informationRepository.findRemindByUser(user);
        model.addAttribute("informations", informationList);
        model.addAttribute("isShare", false);
        return "information/informations";
    }

    @GetMapping("/{informationId}")
    public String getInformationDetails(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long informationId, Model model) {
        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);

        Optional<Information> information = informationRepository.findByInformationIdAndUser(informationId, user);
        if (information.isEmpty())
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
    public String createInformation(@AuthenticationPrincipal MyUserDetails userDetails, @Valid @ModelAttribute("informationDTO") InformationDTO informationDTO, BindingResult result, Model aModel) {
        if(result.hasErrors()) {
            aModel.addAttribute("categories", categoryRepository.findAll());
            return "information/information_add";
        }

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

        setInformationAndModel(model, information);
        model.addAttribute("publishedUser", new PublishedUserDTO());
        model.addAttribute("categories", categoryRepository.findByAllCategoryWithoutCategoryWithId(information.get().getCategory().getCategoryId()));
        return "information/information_edit";
    }

    @PostMapping("/update/{informationId}")
    public String updateInformation(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long informationId, @Valid @ModelAttribute("informationDTO") InformationDTO informationDTO, BindingResult result, Model aModel) {
        if(result.hasErrors()) {
            String login = userDetails.getUsername();
            UserApp user = myUserDetailsService.getUserByLogin(login);
            Optional<Information> information = informationRepository.findByInformationIdAndUser(informationId, user);
            aModel.addAttribute("information", information.get());
            aModel.addAttribute("informationDTO", informationDTO);
            aModel.addAttribute("publishedUser", new PublishedUserDTO());
            aModel.addAttribute("categories", categoryRepository.findByAllCategoryWithoutCategoryWithId(information.get().getCategory().getCategoryId()));
            return "information/information_edit";
        }

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
    public String shareInformation(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long informationId, @Valid @ModelAttribute("publishedUser") PublishedUserDTO publishedUserDTO, BindingResult result, Model aModel) {
        if(result.hasErrors()) {
            String login = userDetails.getUsername();
            UserApp user = myUserDetailsService.getUserByLogin(login);
            Optional<Information> information = informationRepository.findByInformationIdAndUser(informationId, user);

            setInformationAndModel(aModel, information);
            aModel.addAttribute("publishedUser", publishedUserDTO);
            aModel.addAttribute("categories", categoryRepository.findByAllCategoryWithoutCategoryWithId(information.get().getCategory().getCategoryId()));
            return "information/information_edit";
        }

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

    private void setInformationAndModel(Model aModel, Optional<Information> aInformation) {
        InformationDTO informationDTO = new InformationDTO();
        informationDTO.setTitle(aInformation.get().getTitle());
        informationDTO.setContent(aInformation.get().getContent());
        informationDTO.setRemindDate(aInformation.get().getRemindDate());

        aModel.addAttribute("information", aInformation.get());
        aModel.addAttribute("informationDTO", informationDTO);
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
