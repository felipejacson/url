package com.url.controller;

import com.url.dto.CustomResponseDto;
import com.url.dto.UrlDto;
import com.url.util.constants.MessageTypeEnum;
import com.url.util.messages.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UrlController {

	@RequestMapping(value="/", method= {RequestMethod.GET, RequestMethod.POST})
	public String index() {
		return "redirect:/list";
	}

	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(@ModelAttribute("url") UrlDto url, Model model) {
		CustomResponseDto u = null;
		List<UrlDto> list = new ArrayList<>();

		if(url.getId() != null || url.getFriendlyName() != null) {
			ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			HttpEntity<UrlDto> request = new HttpEntity<>(url);
			u = restTemplate.postForObject("http://localhost:8081/urlrest/api/find", request, CustomResponseDto.class);
			list = u.getList();
		}

		model.addAttribute("list", list);
		model.addAttribute("url", url);
		return "ListUrl";
	}

	@RequestMapping(value="/new", method= RequestMethod.GET)
	public String newUrl(Model model) {
		model.addAttribute("url", new UrlDto());
		return "EditUrl";
	}

	@RequestMapping(value="/edit/{id}", method= RequestMethod.GET)
	public String edit(@PathVariable(value="id") Long id, Model model) {
		CustomResponseDto u = null;
		UrlDto url = null;
		if (id != null) {
			ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			u = restTemplate.getForObject("http://localhost:8081/urlrest/api/" + id, CustomResponseDto.class);
			url = u.getData();
		}

		model.addAttribute("url", url);
		return "EditUrl";
	}

	@RequestMapping(value="/save", method= RequestMethod.POST)
	public String save(@ModelAttribute("url") UrlDto url, Model model) {
		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		CustomResponseDto u = null;

		Message message = null;
		String ret = "ListUrl";
		List<UrlDto> list = new ArrayList<>();

		if (url.getId() == null) {
			HttpEntity<UrlDto> request = new HttpEntity<>(url);
			try {
				u = restTemplate.postForObject("http://localhost:8081/urlrest/api/", request, CustomResponseDto.class);
				if(u.getStatus() == HttpStatus.CREATED.value()) {
					list.add(u.getData());
					url = new UrlDto();
					message = new Message(MessageTypeEnum.SUCCESS, "URL saved!");
				}
			} catch (HttpClientErrorException e) {
				message = new Message(MessageTypeEnum.DANGER, "This friendly name alredy exists!");
				ret = "EditUrl";
			}
		} else {
			try {
				restTemplate.put("http://localhost:8081/urlrest/api/" + url.getId(), url);
				list.add(url);
				url = new UrlDto();
				message = new Message(MessageTypeEnum.SUCCESS, "URL saved!");
			} catch (HttpClientErrorException e) {
				message = new Message(MessageTypeEnum.DANGER, "This friendly name alredy exists!");
				ret = "EditUrl";
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("url", url);
		model.addAttribute("message", message);

		return ret;
	}

	@RequestMapping(value="/delete", method= RequestMethod.POST)
	public String delete(@RequestParam("urlList") List<Long> urlList, Model model){
		model.addAttribute("list", new ArrayList<>());
		model.addAttribute("url", new UrlDto());

		if(urlList != null){
			ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
			RestTemplate restTemplate = new RestTemplate(requestFactory);

			for(Long id : urlList){
				CustomResponseDto u = restTemplate.getForObject("http://localhost:8081/urlrest/api/" + id, CustomResponseDto.class);
				if(u != null) {
					restTemplate.delete("http://localhost:8081/urlrest/api/" + u.getData().getId());
				}
			}
			model.addAttribute("message", new Message(MessageTypeEnum.SUCCESS, "URL deleted!"));
		}
		return "ListUrl";
	}

	@GetMapping(path = "/{param}")
	public void redirect(@PathVariable String param, HttpServletResponse response) {
		CustomResponseDto u = null;
		if (param != null) {
			boolean isNumber = false;
			try {
				Long.parseLong(param);
				isNumber = true;
			} catch (NumberFormatException e) { ; }

			ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
			RestTemplate restTemplate = new RestTemplate(requestFactory);

			if(isNumber) {
				u = restTemplate.getForObject("http://localhost:8081/urlrest/api/" + param, CustomResponseDto.class);

				if(u.getData().getId() == null) {
					u = restTemplate.getForObject("http://localhost:8081/urlrest/api/n/" + param, CustomResponseDto.class);
				}
			} else {
				u = restTemplate.getForObject("http://localhost:8081/urlrest/api/n/" + param, CustomResponseDto.class);
			}

			response.addHeader("Location", u.getData().getUrl());
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		} else {
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 5000;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		return clientHttpRequestFactory;
	}

}
