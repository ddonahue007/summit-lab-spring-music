package com.redhat.summit2019.springmusic.api;

import com.redhat.summit2019.springmusic.domain.Trade;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/trades")
@Api("Trades")
public class TradeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);
	private final CrudRepository<Trade, String> repository;

	public TradeController(CrudRepository<Trade, String> repository) {
		this.repository = repository;
	}

	@ApiOperation(value = "Get all trades", notes = "Get all trades", nickname = "get-all-trades")
	@ApiResponses(
		@ApiResponse(code = 200, message = "Success!")
	)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Trade> Trades() {
		return this.repository.findAll();
	}

	@ApiOperation(value = "Adds an trade", notes = "Adds an trade", nickname = "add-trade")
	@ApiResponses(
		@ApiResponse(code = 200, message = "Success!")
	)
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Trade add(@ApiParam(value = "The trade to add", required = true) @RequestBody @Valid Trade trade) {
		LOGGER.info("Adding trade {}", trade.getId());
		return this.repository.save(trade);
	}

	@ApiOperation(value = "Updates an trade", notes = "Updates an trade", nickname = "update-trade")
	@ApiResponses(
		@ApiResponse(code = 200, message = "Success!")
	)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Trade update(@ApiParam(value = "The trade to update", required = true) @RequestBody @Valid Trade trade) {
		LOGGER.info("Updating album {}", trade.getId());
		return this.repository.save(trade);
	}

	@ApiOperation(value = "Get an trade", notes = "Get an trade", nickname = "get-trade")
	@ApiResponses(
		@ApiResponse(code = 200, message = "Success!")
	)
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Trade getById(@ApiParam(value = "The trade id", required = true) @PathVariable String id) {
		LOGGER.info("Getting trade {}", id);
		return this.repository.findById(id).orElse(null);
	}

	@ApiOperation(value = "Delete an trade", notes = "Delete an trade", nickname = "delete-trade")
	@ApiResponses(
		@ApiResponse(code = 204, message = "Success!")
	)
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@ApiParam(value = "The trade id", required = true) @PathVariable String id) {
		LOGGER.info("Deleting trade {}", id);
		this.repository.deleteById(id);
	}
}
