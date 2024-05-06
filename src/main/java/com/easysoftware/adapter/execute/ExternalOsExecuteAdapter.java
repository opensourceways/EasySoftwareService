package com.easysoftware.adapter.execute;

import com.easysoftware.application.externalos.ExternalOsService;
import com.easysoftware.application.externalos.dto.InputExternalOs;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/externalos")
public class ExternalOsExecuteAdapter {


    /**
     * Autowired service responsible for handling External OS related operations.
     */
    @Autowired
    private ExternalOsService externalOsService;

    /**
     * Endpoint to insert a new package mapping.
     *
     * @param input The input data for creating a new package mapping.
     * @return ResponseEntity<Object>.
     */
    @PostMapping("")
    public ResponseEntity<Object> insertPkgMap(@Valid @RequestBody final InputExternalOs input) {
        return externalOsService.insertPkgMap(input);
    }

    /**
     * Endpoint to update an existing package mapping.
     *
     * @param input The input data for updating an existing package mapping.
     * @return ResponseEntity<Object>.
     */
    @PutMapping()
    public ResponseEntity<Object> updatePkgMap(@Valid @RequestBody final InputExternalOs input) {
        return externalOsService.updatePkgMap(input);
    }

    /**
     * Endpoint to delete package mappings associated with specified IDs.
     *
     * @param ids List of IDs of package mappings to be deleted.
     * @return ResponseEntity<Object>.
     */
    @DeleteMapping(value = "/{ids}")
    public ResponseEntity<Object> deletePkgMap(@PathVariable final List<String> ids) {
        return externalOsService.deletePkgMap(ids);
    }
}
