package converter;

import com.scalable.diff.restcompare.model.Links;
import com.scalable.diff.restcompare.model.Self;
import com.scalable.diff.restcompare.model.response.SearchResponse;
import com.scalable.diff.restcompare.rest.ResourceConstants;

import org.springframework.core.convert.converter.Converter;

import com.scalable.diff.restcompare.entity.JsonBinaryDataEntity;

/**
 * The Converter class for mapping the Entity fields to POJO/Domain class.
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
public class JsonBinaryDataEntityToSearchResponseConverter implements Converter<JsonBinaryDataEntity, SearchResponse> {

    /**
     * Performs conversion of Json data entity to SearchResponse POJO class with relevant fields.
     * 
     * @param JsonBinaryDataEntity the JSON Binary Data entity object
     */
    @Override
    public SearchResponse convert(JsonBinaryDataEntity source) {
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setInputId(source.getInputId());
        searchResponse.setEncodedString(source.getEncodedValue());
        searchResponse.setDirection(source.getDirection());
        
        Links links = new Links();
        Self self = new Self();
        
        self.setRef(ResourceConstants.RESOURCE_V1 + "/" + source.getInputId() + "/all");
        links.setSelf(self);
        
        searchResponse.setLinks(links);
        
        return searchResponse;
    }

    
}
