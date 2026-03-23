package com.library.project.library.service;

import com.library.project.library.dto.LibraryInfoDTO;
import com.library.project.library.dto.LibraryStatsDTO;
import java.util.List;

public interface InfoService {
    LibraryInfoDTO getStaticLibraryInfo();

    List<LibraryStatsDTO> getLibraryStatistics();

    // 아래 메서드를 추가하세요!
    LibraryStatsDTO getStat(Long statId);

    // CRUD 추가 부분
    void registerStat(LibraryStatsDTO dto);

    void modifyStat(LibraryStatsDTO dto);

    void removeStat(Long statId);
}