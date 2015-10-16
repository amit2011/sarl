/*
 * Copyright (C) 2014-2015 the original authors or authors.
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
package io.sarl.lang.tests.compilation.aop;

import static org.junit.Assert.assertEquals;
import io.sarl.lang.SARLInjectorProvider;
import io.sarl.tests.api.AbstractSarlTest;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.xbase.compiler.CompilationTestHelper;
import org.eclipse.xtext.xbase.compiler.CompilationTestHelper.Result;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * @author $Author: srodriguez$
 * @author $Author: sgalland$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class AgentCompilerTest extends AbstractSarlTest {

	@Inject
	private CompilationTestHelper compiler;

	@Test
	public void basicAgentCompile() throws Exception {
		String source = "agent A1 { }";
		String expected = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
				);
		this.compiler.assertCompilesTo(source, expected);
	}

	@Test
	public void trueGuardBehaviorUnit() throws Exception {
		final String expectedE1 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Address;",
				"import io.sarl.lang.core.Event;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class E1 extends Event {",
				"  /**",
				"   * Construct an event. The source of the event is unknown.",
				"   */",
				"  @Generated",
				"  public E1() {",
				"    super();",
				"  }",
				"  ",
				"  /**",
				"   * Construct an event.",
				"   * @param source - address of the agent that is emitting this event.",
				"   */",
				"  @Generated",
				"  public E1(final Address source) {",
				"    super(source);",
				"  }",
				"  ",
				"  @Generated",
				"  private final static long serialVersionUID = 588368462L;",
				"}",
				""
				);
		final String expectedA1 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import io.sarl.lang.core.Percept;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  @Percept",
				"  public void _handle_E1_0(final E1 occurrence) {",
				"    System.out.println(occurrence);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
				);
		String source = multilineString(
				"event E1",
				"agent A1 {",
				"  on E1 [ true ] {",
				"    System.out.println(occurrence)",
				"  }",
				"}"
				);
		this.compiler.compile(source, new IAcceptor<CompilationTestHelper.Result>() {
			@Override
			public void accept(Result r) {
				assertEquals(expectedE1,r.getGeneratedCode("E1"));
				assertEquals(expectedA1,r.getGeneratedCode("A1"));
			}
		});
	}

	@Test
	public void falseGuardBehaviorUnit() throws Exception {
		final String expectedE1 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Address;",
				"import io.sarl.lang.core.Event;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class E1 extends Event {",
				"  /**",
				"   * Construct an event. The source of the event is unknown.",
				"   */",
				"  @Generated",
				"  public E1() {",
				"    super();",
				"  }",
				"  ",
				"  /**",
				"   * Construct an event.",
				"   * @param source - address of the agent that is emitting this event.",
				"   */",
				"  @Generated",
				"  public E1(final Address source) {",
				"    super(source);",
				"  }",
				"  ",
				"  @Generated",
				"  private final static long serialVersionUID = 588368462L;",
				"}",
				""
				);
		final String expectedA1 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
				);
		String source = multilineString(
				"event E1",
				"agent A1 {",
				"  on E1 [ false ] {",
				"    System.out.println(occurrence)",
				"  }",
				"}"
				);
		this.compiler.compile(source, new IAcceptor<CompilationTestHelper.Result>() {
			@Override
			public void accept(Result r) {
				assertEquals(expectedE1, r.getGeneratedCode("E1"));
				assertEquals(expectedA1, r.getGeneratedCode("A1"));
			}
		});
	}

	@Test
	public void generalGuardBehaviorUnit() throws Exception {
		final String expectedE1 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Address;",
				"import io.sarl.lang.core.Event;",
				"import org.eclipse.xtext.xbase.lib.Pure;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class E1 extends Event {",
				"  public int i;",
				"  ",
				"  /**",
				"   * Construct an event. The source of the event is unknown.",
				"   */",
				"  @Generated",
				"  public E1() {",
				"    super();",
				"  }",
				"  ",
				"  /**",
				"   * Construct an event.",
				"   * @param source - address of the agent that is emitting this event.",
				"   */",
				"  @Generated",
				"  public E1(final Address source) {",
				"    super(source);",
				"  }",
				"  ",
				"  @Override",
				"  @Pure",
				"  @Generated",
				"  public boolean equals(final Object obj) {",
				"    if (this == obj)",
				"      return true;",
				"    if (obj == null)",
				"      return false;",
				"    if (getClass() != obj.getClass())",
				"      return false;",
				"    E1 other = (E1) obj;",
				"    if (other.i != this.i)",
				"      return false;",
				"    return super.equals(obj);",
				"  }",
				"  ",
				"  @Override",
				"  @Pure",
				"  @Generated",
				"  public int hashCode() {",
				"    final int prime = 31;",
				"    int result = super.hashCode();",
				"    result = prime * result + this.i;",
				"    return result;",
				"  }",
				"  ",
				"  /**",
				"   * Returns a String representation of the E1 event's attributes only.",
				"   */",
				"  @Generated",
				"  @Pure",
				"  protected String attributesToString() {",
				"    StringBuilder result = new StringBuilder(super.attributesToString());",
				"    result.append(\"i  = \").append(this.i);",
				"    return result.toString();",
				"  }",
				"  ",
				"  @Generated",
				"  private final static long serialVersionUID = 588472998L;",
				"}",
				""
				);
		final String expectedA1 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import io.sarl.lang.core.Percept;",
				"import java.util.UUID;",
				"import org.eclipse.xtext.xbase.lib.Pure;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  @Generated",
				"  @Pure",
				"  private boolean _eventhandler_guard_E1_0(final E1 it, final E1 occurrence) {",
				"    return (occurrence.i == 1);",
				"  }",
				"  ",
				"  @Generated",
				"  private void _eventhandler_body_E1_0(final E1 occurrence) {",
				"    System.out.println(occurrence);",
				"  }",
				"  ",
				"  @Percept",
				"  public void _handle_E1_0(final E1 occurrence) {",
				"    if (_eventhandler_guard_E1_0(occurrence, occurrence)) {",
				"      _eventhandler_body_E1_0(occurrence);",
				"    }",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
				);
		String source = multilineString(
				"event E1 { var i : int }",
				"agent A1 {",
				"  on E1 [ occurrence.i === 1 ] {",
				"    System.out.println(occurrence)",
				"  }",
				"}"
				);
		this.compiler.compile(source, new IAcceptor<CompilationTestHelper.Result>() {
			@Override
			public void accept(Result r) {
				assertEquals(expectedE1,r.getGeneratedCode("E1"));
				assertEquals(expectedA1,r.getGeneratedCode("A1"));
			}
		});
	}

	@Test
	public void valueVisibility_0() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				" val myval = 1",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected final int myval = 1;",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void variableVisibility_0() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				" var myval = 1",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected int myval = 1;",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionVisibility_0() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				" def myfct { }",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected void myfct() {",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void valueVisibility_1() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				" private val myval = 1",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  private final int myval = 1;",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void variableVisibility_1() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				" private var myval = 1",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  private int myval = 1;",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionVisibility_1() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				" private def myfct { }",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  private void myfct() {",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void agentmodifier_none() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 { }"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void agentmodifier_public() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"public agent A1 { }"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void agentmodifier_private() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"private agent A1 { }"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"class A1 extends Agent {",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void agentmodifier_abstract() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"abstract agent A1 { }"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public abstract class A1 extends Agent {",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void agentmodifier_abstract_member() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	def fct",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public abstract class A1 extends Agent {",
				"  protected abstract void fct();",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void agentmodifier_final() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"final agent A1 { }"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public final class A1 extends Agent {",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void fieldmodifier_none() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	var field : int",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected int field;",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void fieldmodifier_package() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	package var field : int",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  int field;",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void fieldmodifier_protected() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	protected var field : int",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected int field;",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void fieldmodifier_private() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	private var field : int",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  private int field;",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionmodifier_override() throws Exception {
		String source = multilineString(
				"agent A1 {",
				"	def name",
				"}",
				"agent A2 extends A1 {",
				"	override name {}",
				"}"
			);
		final String expectedA1 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public abstract class A1 extends Agent {",
				"  protected abstract void name();",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			);
		final String expectedA2 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A2 extends A1 {",
				"  @Override",
				"  protected void name() {",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A2(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A2(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			);
		this.compiler.compile(source, new IAcceptor<CompilationTestHelper.Result>() {
			@Override
			public void accept(Result r) {
				assertEquals(expectedA1, r.getGeneratedCode("A1"));
				assertEquals(expectedA2, r.getGeneratedCode("A2"));
			}
		});
	}

	@Test
	public void actionmodifier_none() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	def name {}",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected void name() {",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionmodifier_private() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	private def name {}",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  private void name() {",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionmodifier_package() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	package def name {}",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  void name() {",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionmodifier_protected() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	protected def name {}",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected void name() {",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionmodifier_abstract() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	def name",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public abstract class A1 extends Agent {",
				"  protected abstract void name();",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionmodifier_static() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	static def name {}",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected static void name() {",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionmodifier_dispatch() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	dispatch def name(a : Integer) {}",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected void _name(final Integer a) {",
				"  }",
				"  ",
				"  protected void name(final Integer a) {",
				"    _name(a);",
				"    return;",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionmodifier_dispatch_final() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	dispatch final def name(a : Integer) {}",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected final void _name(final Integer a) {",
				"  }",
				"  ",
				"  protected void name(final Integer a) {",
				"    _name(a);",
				"    return;",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionmodifier_final() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	final def name {}",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected final void name() {",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

	@Test
	public void actionmodifier_synchronized() throws Exception {
		this.compiler.assertCompilesTo(
			multilineString(
				"agent A1 {",
				"	synchronized def name {}",
				"}"
			),
			multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class A1 extends Agent {",
				"  protected synchronized void name() {",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public A1(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				""
			));
	}

}