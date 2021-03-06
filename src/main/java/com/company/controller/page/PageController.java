package com.company.controller.page;

import com.company.dao.api.AccountDAO;
import com.company.dao.api.EventDAO;
import com.company.dao.api.LessonDAO;
import com.company.dao.api.PermissionDAO;
import com.company.dto.*;
import com.company.dto.converter.EntityConverter;
import com.company.model.*;
import com.company.service.auth.CustomUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/")
public class PageController {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final AccountDAO accountDAO;
    private final LessonDAO lessonDAO;
    private final EventDAO eventDAO;
    private final PermissionDAO permissionDAO;
    private final EntityConverter<Account, PrivateAccountDTO> privateAccConverter;
    private final EntityConverter<Account, AccountDTO> accConverter;
    private final EntityConverter<Group, GroupDTO> groupConverter;
    private final EntityConverter<Lesson, LessonDTO> lessonConverter;
    private final EntityConverter<Event, EventDTO> eventConverter;

    @Value("#{systemEnvironment['FACEBOOK_APP_ID']}")
    private String fbAppId;

    @Value("#{systemEnvironment['VK_APP_ID']}")
    private String vkAppId;

    @Autowired
    public PageController(AccountDAO accountDAO, LessonDAO lessonDAO, EventDAO eventDAO, PermissionDAO permissionDAO, EntityConverter<Account, PrivateAccountDTO> privateAccConverter, EntityConverter<Account, AccountDTO> accConverter, EntityConverter<Group, GroupDTO> groupConverter, EntityConverter<Lesson, LessonDTO> lessonConverter, EntityConverter<Event, EventDTO> eventConverter) {
        this.accountDAO = accountDAO;
        this.lessonDAO = lessonDAO;
        this.eventDAO = eventDAO;
        this.permissionDAO = permissionDAO;
        this.privateAccConverter = privateAccConverter;
        this.accConverter = accConverter;
        this.groupConverter = groupConverter;
        this.lessonConverter = lessonConverter;
        this.eventConverter = eventConverter;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getIndexPage(Authentication auth, Model model, Device device) throws JsonProcessingException {
        Account account = readAndInjectHeaderAttributes(auth, model);
        Permission permission = permissionDAO.readByAccount(account.getId());
        if (permission != null) {
            model.addAttribute("canEditLessons", permission.getLessonsEdit());
            model.addAttribute("canEditEvents", permission.getEventsEdit());
        }
        else {
            model.addAttribute("canEditLessons", false);
            model.addAttribute("canEditEvents", false);
        }
        return device.isMobile() ? "mobile/index" : "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(@RequestParam(name = "login_error", required = false, defaultValue = "0") int loginError,
                               Model model, Authentication auth, Device device) {
        if (loginError != 0) {
            model.addAttribute("message", "Login error");
        }

        model.addAttribute("fbAppId", fbAppId);
        model.addAttribute("vkAppId", vkAppId);
        return device.isMobile() ? "mobile/login" : "login";
    }

    @RequestMapping(value = "/new-lesson")
    public String getNewLessonPage(Authentication auth, Model model, Device device) throws JsonProcessingException {
        readAndInjectHeaderAttributes(auth, model);
        return device.isMobile() ? "mobile/newLesson" : "newLesson";
    }

    @RequestMapping(value = "/lesson")
    public String getLessonPage(
                                @RequestParam(name = "id") int lessonId,
                                Authentication auth, Model model, Device device) throws JsonProcessingException {
        readAndInjectHeaderAttributes(auth, model);
        Lesson lesson = lessonDAO.read(lessonId);
        if (lesson == null) {
            model.addAttribute("errorMessage", "Занятие не найдено");
            return "error";
        }
        LessonDTO lessonDTO = lessonConverter.convert(lesson);
        model.addAttribute("lessonDTO", MAPPER.writeValueAsString(lessonDTO));
        return device.isMobile() ? "mobile/lesson" : "lesson";
    }

    @RequestMapping(value = "/event")
    public String getEventPage(
                               @RequestParam(name = "id") int eventId,
                               Authentication auth, Model model, Device device) throws JsonProcessingException {
        readAndInjectHeaderAttributes(auth, model);
        Event event = eventDAO.read(eventId);
        if (event == null) {
            model.addAttribute("errorMessage", "Событие не найдено");
            return "error";
        }
        EventDTO eventDTO = eventConverter.convert(event);
        model.addAttribute("eventDTO", MAPPER.writeValueAsString(eventDTO));
        return device.isMobile() ? "mobile/event" : "event";
    }

    @RequestMapping(value = "/new-event")
    public String getNewEventPage(Authentication auth, Model model, Device device) throws JsonProcessingException {
        readAndInjectHeaderAttributes(auth, model);
        return device.isMobile() ? "mobile/newEvent" : "newEvent";
    }

    @RequestMapping(value = "/account")
    public String getAccountPage(Authentication auth, Model model, Device device) throws JsonProcessingException {
        readAndInjectHeaderAttributes(auth, model);
        return device.isMobile() ? "mobile/account" : "account";
    }


    @RequestMapping(value = "/group")
    public String getGroupPage(Authentication auth, Model model, Device device) throws JsonProcessingException {
        Account account = readAndInjectHeaderAttributes(auth, model);
        Group group = account.getGroup();
        if (group == null) {
            model.addAttribute("errorMessage", "Вы не состоите в группе");
            return "error";
        }
        int groupId = group.getId();

        List<AccountDTO> membersDTO = accConverter.convert(accountDAO.readByGroup(groupId));

        model.addAttribute("membersDTO", MAPPER.writeValueAsString(membersDTO));
        model.addAttribute("permissionsDTO", MAPPER.writeValueAsString(permissionDAO.readByGroup(groupId)));
        return device.isMobile() ? "mobile/group" : "group";
    }

    private Account readAndInjectHeaderAttributes(Authentication auth, Model model) throws JsonProcessingException {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("Authentication principal should implement " + CustomUserDetails.class.getName());
        }

        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Account account = accountDAO.read(accountId);
        PrivateAccountDTO accountDTO = privateAccConverter.convert(account);
        GroupDTO groupDTO = groupConverter.convert(account.getGroup());
        model.addAttribute("accountDTO", MAPPER.writeValueAsString(accountDTO));
        model.addAttribute("groupDTO", MAPPER.writeValueAsString(groupDTO));

        return account;
    }
}
