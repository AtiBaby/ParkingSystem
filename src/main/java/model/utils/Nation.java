package model.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by holhosa on 2017.10.05..
 */
@Getter
@AllArgsConstructor
public enum Nation {

    CZECH("Czech","cs"),
    GERMANY("Germany","de"),
    ENGLISH("English","en"),
    SPANISH("Spanish","es"),
    HUNGARY("Hungary","hu"),
    ITALY("Italy","it"),
    POLAND("Poland","pl"),
    ROMANIAN("Romanian","ro"),
    SLOVAK("Slovak","sk");

    private String name;
    private String shortName;
}
