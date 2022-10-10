package de.egosanto.ms.moviecatalog.resources;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.egosanto.ms.moviecatalog.models.CatalogItem;

/**
 * 
 * @author Toni Zeidler
 *
 */
@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog (@PathVariable("userId") String userId) {
		return Collections.singletonList(
			new CatalogItem("Transformers", "Test", 4)
		);
	}
}
