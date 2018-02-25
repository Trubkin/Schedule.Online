package com.company.service.auth.oauth2;

import java.io.IOException;
import java.util.Map;

public interface OAuth2DataRestorer {
    String restoreAccessToken(String data) throws IOException;

    OAuth2Account restoreAccountData(String data) throws IOException;
}
