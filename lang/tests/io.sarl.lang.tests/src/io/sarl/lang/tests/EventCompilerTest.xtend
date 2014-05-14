/*
 * Copyright 2014 Sebastian RODRIGUEZ, Nicolas GAUD, Stéphane GALLAND.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package io.sarl.lang.tests

import com.google.inject.Inject
import io.sarl.lang.SARLInjectorProvider
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.xbase.compiler.CompilationTestHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author $Author: srodriguez$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@RunWith(XtextRunner)
@InjectWith(SARLInjectorProvider)
class EventCompilerTest {
	@Inject extension CompilationTestHelper

	@Test
	def basicCompile() {
			'''
			event E1 {
				
			}
		'''.assertCompilesTo('''
			import io.sarl.lang.core.Event;

			@SuppressWarnings("all")
			public class E1 extends Event {
			  /**
			   * Returns a String representation of the Event E1.
			   */
			  public String toString() {
			    StringBuilder result = new StringBuilder();
			    result.append("E1[");
			    result.append(attributesToString());
			    result.append("]");
			    return result.toString();
			  }
			}
		''')
	}
	
	@Test
	def withVarAttributesCompile(){
		'''
		event E1 {
			var name : String
		}
		'''.assertCompilesTo('''
		import io.sarl.lang.core.Event;

		@SuppressWarnings("all")
		public class E1 extends Event {
		  private String name;
		  
		  public String getName() {
		    return this.name;
		  }
		  
		  public void setName(final String name) {
		    this.name = name;
		  }
		  
		  @Override
		  public boolean equals(final Object obj) {
		    if (this == obj)
		      return true;
		    if (obj == null)
		      return false;
		    if (getClass() != obj.getClass())
		      return false;
		    if (!super.equals(obj))
		      return false;
		    E1 other = (E1) obj;
		    if (name == null) {
		      if (other.name != null)
		        return false;
		    } else if (!name.equals(other.name))
		      return false;
		    return true;
		  }
		  
		  @Override
		  public int hashCode() {
		    final int prime = 31;
		    int result = super.hashCode();
		    result = prime * result + ((name== null) ? 0 : name.hashCode());
		    return result;
		  }
		  
		  /**
		   * Returns a String representation of the Event E1 attributes only.
		   */
		  protected String attributesToString() {
		    StringBuilder result = new StringBuilder(super.attributesToString());
		    result.append("name  = ").append(this.name);
		    return result.toString();
		  }
		  
		  /**
		   * Returns a String representation of the Event E1.
		   */
		  public String toString() {
		    StringBuilder result = new StringBuilder();
		    result.append("E1[");
		    result.append(attributesToString());
		    result.append("]");
		    return result.toString();
		  }
		}
		''')
	}
	
	@Test
	def inheritanceCompile(){
		val expectedE2 = '''
		@SuppressWarnings("all")
		public class E2 extends E1 {
		  /**
		   * Returns a String representation of the Event E2.
		   */
		  public String toString() {
		    StringBuilder result = new StringBuilder();
		    result.append("E2[");
		    result.append(attributesToString());
		    result.append("]");
		    return result.toString();
		  }
		}
		'''
		
		'''
			event E1 {
				var name : String
			}
			
			event E2 extends E1{
				
			}
		'''.compile([CompilationTestHelper.Result r |
			Assert.assertEquals(expectedE2,r.getGeneratedCode("E2"))
		]);
	}
}