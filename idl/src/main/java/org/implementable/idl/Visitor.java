package org.implementable.idl;

import org.antlr.v4.runtime.tree.RuleNode;
import org.implementable.idl.parser.IDLBaseVisitor;
import org.implementable.idl.parser.IDLParser;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Visitor extends IDLBaseVisitor<Node> {

    @Override
    protected Node defaultResult() {
        return new NodeList();
    }

    @Override
    protected Node aggregateResult(Node aggregate, Node nextResult) {
        assert(aggregate instanceof NodeList);
        ((NodeList) aggregate).add(nextResult);
        return aggregate;
    }

    @Override
    public Node visitSpecification(IDLParser.SpecificationContext ctx) {
        Node definitions = visitChildren(ctx);
        assert(definitions instanceof NodeList);
        Specification specification = new Specification((NodeList) definitions);
        return specification;
    }

    @Override
    public Node visitNamespace_decl(IDLParser.Namespace_declContext ctx) {
        Node children = visitChildren(ctx);
        assert(children instanceof NodeList);
        NodeList nodes = new NodeList(((NodeList) children).stream().filter(child -> !(child instanceof NodeList)).
                collect(Collectors.toList()));
        return new Namespace(ctx.id.getText(), new Specification(nodes));
    }

    @Override
    public Node visitDefinition(IDLParser.DefinitionContext ctx) {
        return singleElement(visitChildren(ctx));
    }

    @Override
    public Node visitInterface_decl(IDLParser.Interface_declContext ctx) {
        Node children = visitChildren(ctx.interface_body());
        assert(children instanceof NodeList);
        Interface anInterface = new Interface((TypeSpec) visitType_spec(ctx.type_spec()),
                                              (NodeList) children);

        if (ctx.interface_inheritance_list() != null) {
            Node inheritanceList = visitInterface_inheritance_list(ctx.interface_inheritance_list());
            assert(inheritanceList instanceof NodeList);
            anInterface.setInheritance((List<TypeSpec>) inheritanceList);
        }

        return anInterface;
    }

    @Override
    public Node visitInterface_inheritance_list(IDLParser.Interface_inheritance_listContext ctx) {
        return visitInterface_inheritance(ctx.interface_inheritance());
    }

    @Override
    public Node visitInterface_inheritance(IDLParser.Interface_inheritanceContext ctx) {
        return new NodeList(ctx.type_spec().stream().map(this::visitType_spec).collect(Collectors.toList()));
    }

    @Override
    public Node visitInterface_element(IDLParser.Interface_elementContext ctx) {
        return singleElement(visitChildren(ctx));
    }

    // Function
    @Override
    public Node visitFunction_decl(IDLParser.Function_declContext ctx) {
        Node argumentsDecl = visitArguments_decl(ctx.arguments_decl());
        assert(argumentsDecl instanceof NodeList);
        Function function = new Function();
        function.setType(((TypeSpec) visitType_spec(ctx.type_spec())));
        function.setIdentifier(ctx.id.getText());
        function.setArguments((List<Function.Argument>) argumentsDecl);
        return function;
    }

    @Override
    public Node visitArguments_decl(IDLParser.Arguments_declContext ctx) {
        Node children = visitChildren(ctx);
        assert(children instanceof NodeList);
        return new NodeList(
        ((NodeList) children).stream().
                filter(node -> node instanceof Function.Argument).
                collect(Collectors.toList()));
    }

    @Override
    public Node visitArgument_decl(IDLParser.Argument_declContext ctx) {
        return new Function.Argument((TypeSpec) visitType_spec(ctx.type_spec()), ctx.id.getText());
    }

    // Struct

    @Override
    public Node visitStruct_decl(IDLParser.Struct_declContext ctx) {
        Struct struct = new Struct((TypeSpec) visitType_spec(ctx.type_spec()));
        if (ctx.struct_members() != null) {
            Node members = visitStruct_members(ctx.struct_members());
            assert(members instanceof NodeList);
            struct.setMembers((List<Struct.Member>) members);
        }
        return struct;
    }

    @Override
    public Node visitStruct_members(IDLParser.Struct_membersContext ctx) {
        Node children = visitChildren(ctx);
        assert (children instanceof NodeList);
        return new NodeList(
                ((NodeList) children).stream().
                        filter(node -> node instanceof Struct.Member).
                        collect(Collectors.toList()));
    }

    @Override
    public Node visitStruct_member(IDLParser.Struct_memberContext ctx) {
        return new Struct.Member((TypeSpec) visitType_spec(ctx.type_spec()), ctx.id.getText());
    }

    // Type
    @Override
    public Node visitType_spec(IDLParser.Type_specContext ctx) {
        TypeSpec typeSpec = new TypeSpec(ctx.id.getText());
        if (ctx.template() != null) {
            Node template = visitTemplate(ctx.template());
            assert(template instanceof TypeSpec.Template);
            typeSpec.setTemplate((TypeSpec.Template) template);
        }
        return typeSpec;
    }

    @Override
    public Node visitTemplate(IDLParser.TemplateContext ctx) {
        return
            new TypeSpec.Template((List<TypeSpec.Template.Component>)
                visitTemplate_components(ctx.template_components()));
    }

    @Override
    public Node visitTemplate_component(IDLParser.Template_componentContext ctx) {
        List<TypeSpec> inheritance = new LinkedList<>();
        if (ctx.interface_inheritance_list() != null) {
            inheritance = (List<TypeSpec>) visitInterface_inheritance_list(ctx.interface_inheritance_list());
        }
        return new TypeSpec.Template.Component((TypeSpec) visitType_spec(ctx.type_spec()), inheritance);
    }

    /**
     * singleElement asserts that the node is a NodeList and contains one and
     * only one element and returns it
     * @param node
     * @return the only node list element
     */
    private Node singleElement(Node node) {
        assert(node instanceof NodeList);
        assert(((NodeList) node).size() == 1);
        return ((NodeList) node).getFirst();
    }
}
