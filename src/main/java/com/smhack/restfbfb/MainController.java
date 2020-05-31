package com.smhack.restfbfb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Post;

@Controller
@RequestMapping("/")
public class MainController {

	@GetMapping("/test")
	public String test(Model model) {

		String accessToken = "EAAZAHPOei7u4BAK8uUy3xumKTCFwXmj8e7tBwZApvIj5WWECcGum7rE4X3CcGioAVuWDw5ZBiE4S2ZANBAOfmupWWuAgKxoaWHk1BkrFrdbynGwyAoihWzlr8I6FFyLtnbhpwjzddLZAkikCn3VGjvTfVOzpIwy1lcUAwfO4Gvz00ZCtkkAjoM5TZBciLhKp8ZAtUaMGQHUKbgZDZD";
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);
		
		
		Connection<Post> pageFeed = facebookClient.fetchConnection("me/posts", Post.class, Parameter.with("fields",
				"from,to,likes.summary(true),comments.summary(true), reactions.summary(true), picture, fullPicture, permalinkUrl"));
		
		// System.out.println(pageFeed.getData().get(0).getLikesCount());

		// System.out.println(pageFeed.getData().get(0).getReactions().getTotalCount());

		int numberOfPosts = pageFeed.getData().size();
		
		long reactions = 0;
		Post thePostWithHighReactions = pageFeed.getData().get(0);
		for (Post postData : pageFeed.getData()) {
			
			if(reactions < postData.getReactions().getTotalCount()) {
				thePostWithHighReactions = postData;
			}
		}
		model.addAttribute("thePost", thePostWithHighReactions );

		return "home.html";

	}

}
