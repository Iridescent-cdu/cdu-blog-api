package service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import model.STSToken;
import service.STSTokenService;

public class STSTokenServiceImpl implements STSTokenService {
    public STSToken getSTSToken() {
        // STS接入地址，例如sts.cn-hangzhou.aliyuncs.com。
        String endpoint = "sts.cn-chengdu.aliyuncs.com";
        // 填写步骤1生成的RAM用户访问密钥AccessKey ID和AccessKey Secret。
        String AccessKeyId = "LTAI5tParhtV5BX55oDWktMr";
        String AccessKeySecret = "eqywbmhtdJv9djDjXoKgHPkCxVMfX2";
        // 填写步骤3获取的角色ARN。
        String roleArn = "acs:ram::1285671690456705:role/iridescent";
        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
        String roleSessionName = "cdu-blog-api";
        // 以下Policy用于限制仅允许使用临时访问凭证向目标存储空间examplebucket上传文件。
        // 临时访问凭证最后获得的权限是步骤4设置的角色权限和该Policy设置权限的交集，即仅允许将文件上传至目标存储空间examplebucket下的exampledir目录。
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:PutObject\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:cdu-blog-api/*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            // regionId表示RAM的地域ID。以华东1（杭州）地域为例，regionID填写为cn-hangzhou。也可以保留默认值，默认值为空字符串（""）。
            String regionId = "cn-chengdu";
            // 添加endpoint。适用于Java SDK 3.12.0及以上版本。
            DefaultProfile.addEndpoint(regionId, "Sts", endpoint);
            // 添加endpoint。适用于Java SDK 3.12.0以下版本。
            // DefaultProfile.addEndpoint("",regionId, "Sts", endpoint);
            // 构造default profile。
            IClientProfile profile = DefaultProfile.getProfile(regionId, AccessKeyId, AccessKeySecret);
            // 构造client。
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            // 适用于Java SDK 3.12.0及以上版本。
            request.setSysMethod(MethodType.POST);
            // 适用于Java SDK 3.12.0以下版本。
            //request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy); // 如果policy为空，则用户将获得该角色下所有权限。
            request.setDurationSeconds(3600L); // 设置临时访问凭证的有效时间为3600秒。
            final AssumeRoleResponse response = client.getAcsResponse(request);

            String expiration = response.getCredentials().getExpiration();
            String accessKeyId = response.getCredentials().getAccessKeyId();
            String accessKeySecret = response.getCredentials().getAccessKeySecret();
            String securityToken = response.getCredentials().getSecurityToken();
            String requestId = response.getRequestId();

            //为STSToken实体类赋值
            STSToken stsToken = new STSToken(accessKeyId, accessKeySecret, securityToken);

//            System.out.println("Expiration: " + expiration);
//            System.out.println("Access Key Id: " + accessKeyId);
//            System.out.println("Access Key Secret: " + accessKeySecret);
//            System.out.println("Security Token: " + securityToken);
//            System.out.println("RequestId: " + requestId);

            return stsToken;
        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            System.out.println("RequestId: " + e.getRequestId());
        }
        return null;
    }
}

