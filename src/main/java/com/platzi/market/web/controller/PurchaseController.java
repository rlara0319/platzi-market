package com.platzi.market.web.controller;

import com.platzi.market.domain.Purchase;
import com.platzi.market.domain.service.PurchaseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
  @Autowired private PurchaseService purchaseService;

  @GetMapping("/all")
  @ApiOperation("Get all supermarket purchases")
  @ApiResponse(code = 200, message = "OK")
  public ResponseEntity<List<Purchase>> getAll() {
    return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/client/{id}")
  @ApiOperation("Search purchases by client ID")
  @ApiResponses({
    @ApiResponse(code = 200, message = "OK"),
    @ApiResponse(code = 404, message = "Product not found")
  })
  public ResponseEntity<List<Purchase>> getByClient(
      @ApiParam(value = "The id of the client", required = true, example = "4546221")
          @PathVariable("id")
          String clientId) {
    return purchaseService
        .getByClient(clientId)
        .map(purchases -> new ResponseEntity<>(purchases, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping("/save")
  @ApiOperation("Creates a new purchase")
  @ApiResponse(code = 201, message = "Purchase created")
  public ResponseEntity<Purchase> save(
      @ApiParam(value = "Purchase to create", required = true) @RequestBody
          Purchase purchase) {
    return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
  }
}
