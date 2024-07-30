package cs3220.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cs3220.model.UserEntry;
import cs3220.repository.UserRepository;

@Controller
public class IndexController {
	public IndexController() {

	}

	@RequestMapping("/")
	public String index(@ModelAttribute("users") UserEntry newEntry, BindingResult result) {
		return "main";
	}

}
