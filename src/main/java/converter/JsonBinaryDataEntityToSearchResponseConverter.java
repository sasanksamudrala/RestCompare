package converter;

import com.scalable.diff.restcompare.model.Links;
import com.scalable.diff.restcompare.model.Self;
import com.scalable.diff.restcompare.model.response.SearchResponse;
import com.scalable.diff.restcompare.rest.ResourceConstants;

import org.springframework.core.convert.converter.Converter;

import com.scalable.diff.restcompare.entity.JsonBinaryDataEntity;

public class JsonBinaryDataEntityToSearchResponseConverter implements Converter<JsonBinaryDataEntity, SearchResponse> {

	@Override
	public SearchResponse convert(JsonBinaryDataEntity source) {
		// TODO Auto-generated method stub
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
