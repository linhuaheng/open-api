//import com.wwwarehouse.commons.utils.EnumUtil;
//import com.wwwarehouse.xdw.openapi.model.BaDefinedCode;
//
//import java.io.IOException;
//
///**
// * Created by yjj on 2016/12/26 0026.
// */
//public class EnumGenerator {
//	public static void main(String[] args) throws IOException {
//		BaDefinedCode baDefinedCode = new BaDefinedCode();
//		String filePath = baDefinedCode.getClass().getResource("").getPath();
//		filePath = filePath.substring(0, filePath.indexOf("-common") + 7);
//		String r = EnumUtil.createEuumFile("com.wwwarehouse.xdw.openapi.enums", "DatasyncDefinedCode", filePath);
//		System.out.println(r);
//	}
//
//}
