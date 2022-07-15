package com.example.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("upload")
public class UploadController {

    private final Path basePath = Paths.get("./src/main/resources/upload/");

    @PostMapping(value = "file/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Void> upload(@RequestPart("user-name") String name,
                             @RequestPart("fileToUpload") Mono<FilePart> filePartMono){
        System.out.println("user : " + name);
        return  filePartMono
                .doOnNext(fp -> System.out.println("Received File : " + fp.filename()))
                .flatMap(fp -> fp.transferTo(basePath.resolve(fp.filename())))
                .then();
    }

}