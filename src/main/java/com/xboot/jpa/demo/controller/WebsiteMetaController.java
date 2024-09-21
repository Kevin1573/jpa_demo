package com.xboot.jpa.demo.controller;

import com.xboot.jpa.demo.common.resp.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注释
 *
 * @author xboot
 **/
@RestController
@RequestMapping("/website")
public class WebsiteMetaController {
    @RequestMapping("/meta")
    public Result getSiteMetaInfo() {

        return Result.ok().data("title", "GITHUB | github")
                .data("keywords", "Open Source,git,github")
                .data("description", "Drag and Drop for React. Contribute to react-dnd/react-dnd development by creating an account on GitHub.")
                .data("url", "https://github.com")
                .data("iconUrl", "https://github.com/fluidicon.png");
    }
}
