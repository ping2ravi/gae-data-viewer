import com.next.viewer.client.beans.EntityColDefinitionBean;
import com.next.viewer.server.entity.EntityDefnition;
import com.next.viewer.server.exceptions.NoSuchENtity;
import com.next.viewer.server.util.ReflectionUtil;


public class ReflectionTest {

	public static void main(String[] args) throws NoSuchENtity{
		EntityColDefinitionBean[] beans = ReflectionUtil.getClassFields(EntityDefnition.class.getName());
		for(EntityColDefinitionBean oneBean:beans){
			System.out.println("oneBean : "+oneBean.getFieldName());
		}

	}
}
