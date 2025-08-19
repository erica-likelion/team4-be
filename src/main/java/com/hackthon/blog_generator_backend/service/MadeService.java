package com.hackthon.blog_generator_backend.service;

import com.hackthon.blog_generator_backend.dto.MadeRequestDto;
import com.hackthon.blog_generator_backend.dto.MadeResponseDto;
import com.hackthon.blog_generator_backend.dto.UserInputRequestDto;
import com.hackthon.blog_generator_backend.entity.Made;
import com.hackthon.blog_generator_backend.entity.Store;
import com.hackthon.blog_generator_backend.repository.MadeRepository;
import com.hackthon.blog_generator_backend.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MadeService {
    
    private final MadeRepository madeRepository;
    private final StoreRepository storeRepository;
    
    // Made 생성 (POST)
    @Transactional
    public MadeResponseDto createMade(MadeRequestDto requestDto) {
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));
        
        Made made = Made.builder()
                .image(requestDto.getImage())
                .hashTag(requestDto.getHashTag())
                .prompt(requestDto.getPrompt())
                .userInput(requestDto.getUserInput())
                .resultTitle(requestDto.getResultTitle())
                .resultContent(requestDto.getResultContent())
                .store(store)
                .build();
        
        Made savedMade = madeRepository.save(made);
        
        return convertToResponseDto(savedMade);
    }
    
    // 결과 조회 (GET)
    public MadeResponseDto getResult(Long madeId) {
        Made made = madeRepository.findById(madeId)
                .orElseThrow(() -> new RuntimeException("Made not found"));
        
        return convertToResponseDto(made);
    }
    
    // 사용자 입력 처리 (POST)
    @Transactional
    public MadeResponseDto processUserInput(UserInputRequestDto requestDto) {
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));
        
        // 여기에 실제 블로그 생성 로직이 들어갈 예정
        // 현재는 임시로 기본값으로 Made를 생성
        Made made = Made.builder()
                .userInput(requestDto.getUserInput())
                .prompt(requestDto.getPrompt())
                .store(store)
                .resultTitle("Generated Title") // 실제로는 AI로 생성
                .resultContent("Generated Content") // 실제로는 AI로 생성
                .hashTag("#generated")
                .build();
        
        Made savedMade = madeRepository.save(made);
        
        return convertToResponseDto(savedMade);
    }
    
    // Made 목록 조회
    public List<MadeResponseDto> getAllMade() {
        List<Made> madeList = madeRepository.findAll();
        return madeList.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    // DTO 변환 메서드
    private MadeResponseDto convertToResponseDto(Made made) {
        return MadeResponseDto.builder()
                .madeId(made.getMadeId())
                .image(made.getImage())
                .hashTag(made.getHashTag())
                .prompt(made.getPrompt())
                .userInput(made.getUserInput())
                .resultTitle(made.getResultTitle())
                .resultContent(made.getResultContent())
                .storeId(made.getStore().getStoreId())
                .storeName(made.getStore().getStoreName())
                .build();
    }
}
