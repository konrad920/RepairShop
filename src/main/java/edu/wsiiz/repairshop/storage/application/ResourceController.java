package edu.wsiiz.repairshop.storage.application;

import edu.wsiiz.repairshop.storage.domain.storage.Resource;
import edu.wsiiz.repairshop.storage.domain.storage.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class ResourceController {

  final ResourceRepository repository;
  final ResourceService service;

  @GetMapping("/global")
  public ResponseEntity<List<Resource>> getMany() {

    return ResponseEntity.ok(repository.findAll());
  }

}
