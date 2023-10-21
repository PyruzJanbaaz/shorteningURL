package com.pyruz.shortening.service;

import com.pyruz.shortening.handler.ApplicationProperties;
import com.pyruz.shortening.handler.UrlHandler;
import com.pyruz.shortening.model.domain.UrlBean;
import com.pyruz.shortening.model.dto.UrlDTO;
import com.pyruz.shortening.model.dto.base.BaseDTO;
import com.pyruz.shortening.model.entiry.Url;
import com.pyruz.shortening.model.mapper.UrlMapper;
import com.pyruz.shortening.repository.ReviewRepository;
import com.pyruz.shortening.repository.UrlRepository;
import com.pyruz.shortening.service.impl.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {
    @InjectMocks
    private UrlService urlService;
    @Mock
    private UrlRepository urlRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ApplicationProperties applicationProperties;
    @Mock
    private UrlMapper urlMapper;
    private Url url;

    @BeforeEach
    public void setup() {
        urlMapper = Mappers.getMapper(UrlMapper.class);
        urlService = new UrlService(
                urlRepository,
                reviewRepository,
                applicationProperties,
                urlMapper
        );
        url = Url.builder()
                .id(1L)
                .shortURL("ERt450")
                .originalURL("https://www.mercedes-arena-stuttgart.de/en/inovation")
                .build();
    }

    @Test
    @DisplayName("It should generate and save a shortening URL")
    void generateShortURL_Success() {
        when(urlRepository.save(any(Url.class))).thenReturn(url);
        UrlBean urlBean = UrlBean.builder()
                .url("https://www.mercedes-arena-stuttgart.de/en/inovation")
                .build();
        BaseDTO baseDTO = urlService.generateShortURL(urlBean);
        assertThat(baseDTO.getData()).isNotNull();
        assertThat(((UrlDTO) baseDTO.getData()).getShortURL()).isEqualTo(UrlHandler.getFullShorURL(url));
    }


}
