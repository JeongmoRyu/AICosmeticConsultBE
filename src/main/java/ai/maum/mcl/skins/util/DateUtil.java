package ai.maum.mcl.skins.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;

@Slf4j
public class DateUtil {
    public static Integer calculateAge(String birthDate, boolean isLunarCalendar) {
        Integer age = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthLocalDate = LocalDate.parse(birthDate, formatter);
            LocalDate currentDate = LocalDate.now();

            if (isLunarCalendar) {
                // 음력인 경우 생년월일을 양력으로 변환
                // (음력 생년월일을 양력으로 변환하는 로직 추가)
                Chronology lunarChronology = IsoChronology.INSTANCE;
                birthLocalDate = LocalDate.from(lunarChronology.date(birthLocalDate));
            }
            age = currentDate.getYear() - birthLocalDate.getYear();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return age;
    }
}
