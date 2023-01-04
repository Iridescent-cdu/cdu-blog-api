package utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;

public class OSSUtil {
    //地域节点
    private static String ENDPOINT = "oss-cn-chengdu.aliyuncs.com";
    //访问ID
    private static String ACCESSKEYID = "LTAI5tFqr9PbQqndaAZkC7bM";
    //访问秘钥
    private static String ACCESSKEYSECRET = "cC8txWnTQ2mCafut9vDQpcSg9Hntai";
    //仓库名称
    private static String BUCKETNAME = "cdu-blog-api";
    //外网域名
    private static String SUFFER_URL = "https://cdu-blog-api.oss-cn-chengdu.aliyuncs.com/";

    /**
     * 获取OSS链接
     */
    public OSSClient getOSSClient() {
        //创建一个OSSClient对象
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        //判断仓库是否存在
        if (ossClient.doesBucketExist(BUCKETNAME)) {
            System.out.println("bucket存在");
        } else {
            //通过api接口 形式创建bucket仓库
            CreateBucketRequest bucketRequest = new CreateBucketRequest(null);
            bucketRequest.setBucketName(BUCKETNAME);
            bucketRequest.setCannedACL(CannedAccessControlList.Private);
            ossClient.createBucket(bucketRequest);
            ossClient.shutdown();
        }
        return ossClient;
    }

    public static void main(String[] args) {
        OSSUtil ossUtil = new OSSUtil();
        ossUtil.getOSSClient();
    }
}
