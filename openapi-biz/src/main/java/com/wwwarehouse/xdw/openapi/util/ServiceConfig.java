package com.wwwarehouse.xdw.openapi.util;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.wwwarehouse.commons.utils.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**
 * Created by zhigang.huang on 2017/11/1.
 */
@Service
@Scope("singleton")
@DisconfFile(filename = "service-config.properties")
public class ServiceConfig {
    private String fileHttpsPath;
    private String fileWebPath;
    private String unifiHost;
    private String unifiUserName;
    private String unifiPassword;
    /**
     * 秘钥：公钥
     */
    private String iscsPublicKey;
    /**
     * 秘钥：私钥
     */
    private String iscsPrivateKey;

    @DisconfFileItem(name = "iscs.public.key", associateField = "iscsPublicKey")
    public String getIscsPublicKey() {
        return iscsPublicKey;
    }

    public void setIscsPublicKey(String iscsPublicKey) {
        this.iscsPublicKey = iscsPublicKey;
    }

    @DisconfFileItem(name = "iscs.private.key", associateField = "iscsPrivateKey")
    public String getIscsPrivateKey() {
        return iscsPrivateKey;
    }

    public void setIscsPrivateKey(String iscsPrivateKey) {
        this.iscsPrivateKey = iscsPrivateKey;
    }

    @DisconfFileItem(name = "file_https_path", associateField = "fileHttpsPath")
    public String getFileHttpsPath() {
        return fileHttpsPath;
    }

    @DisconfFileItem(name = "file_web_path", associateField = "fileWebPath")
    public String getFileWebPath() {
        return fileWebPath;
    }

    @DisconfFileItem(name = "unifi.host", associateField = "unifiHost")
    public String getUnifiHost() {
        return this.unifiHost;
    }

    @DisconfFileItem(name = "unifi.username", associateField = "unifiUserName")
    public String getUnifiUserName() {
        return this.unifiUserName;
    }

    @DisconfFileItem(name = "unifi.password", associateField = "unifiPassword")
    public String getUnifiPassword() {
        return this.unifiPassword;
    }

    public String getPicPath(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (path.startsWith("http://") || path.startsWith("https://")) {
            return path;
        }
        if (getFileHttpsPath().endsWith("/") && path.startsWith("/")) {
            path = path.substring(1);
        }
        return getFileHttpsPath() + path;
    }
}
