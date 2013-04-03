beanmeta
====

Beanmeta framework is a utility of java-bean.

## Modules
 * beanmeta-core is a core module for this beanmeta framework.
 * beanmeta-gen generate meta class of java-bean using APT.
 * beanmeta-pom is parent project for maven.
 * beanmeta-test is test project for beanmeta-gen.
 * beanmeta-util is utility module using beanmeta framework.

## Usage



### Define Annotation

```
@SuppressWarnings("serial")
@Model(parent=Person.class)
public class Account implements Serializable{
	@Key
	private String email;
	@Attribute
	private String address;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address = address;
	}
}
```