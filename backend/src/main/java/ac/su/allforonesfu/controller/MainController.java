package ac.su.allforonesfu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ac.su.sfu.service.ChatServiceMain;
import ac.su.sfu.service.social.PrincipalDetails;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final ChatServiceMain chatServiceMain;

    @GetMapping("/")
    public String goChatRoom(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        model.addAttribute("list", chatServiceMain.findAllRoom());

        if (principalDetails != null) {
            model.addAttribute("user", principalDetails.getUser());
            log.debug("user [{}] ", principalDetails);
        }

        log.debug("SHOW ALL ChatList {}", chatServiceMain.findAllRoom());
        return "roomlist";
    }
}
