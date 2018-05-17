package com.wwwarehouse.xdw.openapi.web.util;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.wwwarehouse.commons.utils.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**
 * Created by zhigang.huang on 2017/11/27.
 */
@Service
@Scope("singleton")
@DisconfFile(filename = "web-config.properties")
public class WebConfig {
    private String fileHttpsPath;
    private String fileWebPath;

    @DisconfFileItem(name = "file_https_path", associateField = "fileHttpsPath")
    public String getFileHttpsPath() {
        return fileHttpsPath;
    }

    @DisconfFileItem(name = "file_web_path", associateField = "fileWebPath")
    public String getFileWebPath() {
        return fileWebPath;
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
