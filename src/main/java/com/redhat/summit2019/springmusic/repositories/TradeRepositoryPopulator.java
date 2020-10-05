package com.redhat.summit2019.springmusic.repositories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.summit2019.springmusic.domain.Trade;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.init.Jackson2ResourceReader;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class TradeRepositoryPopulator implements ApplicationListener<ApplicationReadyEvent> {
	private final Jackson2ResourceReader resourceReader;
	private final Resource sourceData;

	public TradeRepositoryPopulator() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.resourceReader = new Jackson2ResourceReader(mapper);
		this.sourceData = new ClassPathResource("trades.json");
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		Optional.ofNullable(BeanFactoryUtils.beanOfTypeIncludingAncestors(event.getApplicationContext(), CrudRepository.class))
			.filter(repo -> repo.count() == 0)
			.ifPresent(this::populate);
	}

	private void populate(CrudRepository repository) {
		Object entity = getEntityFromResource(this.sourceData);

		if (entity instanceof Collection) {
			((Collection<Trade>) entity).stream()
				.filter(Objects::nonNull)
				.forEach(repository::save);
		}
		else {
			repository.save(entity);
		}
	}

	private Object getEntityFromResource(Resource resource) {
		try {
			return this.resourceReader.readFrom(resource, this.getClass().getClassLoader());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
