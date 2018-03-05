package com.arch.annomodule;

import com.google.auto.service.AutoService;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.arch.annomodule.Constants.ACTIVITY;
import static com.arch.annomodule.Constants.ANNOTATION_TYPE_ROUTER;
import static com.arch.annomodule.Constants.ANNOTATION_TYPE_ROUTE_NODE;
import static com.arch.annomodule.Constants.KEY_HOST_NAME;

//@SupportedAnnotationTypes(“<待处理注解类路径>”)
//@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
@SupportedOptions(KEY_HOST_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({ANNOTATION_TYPE_ROUTE_NODE, ANNOTATION_TYPE_ROUTER})
public class annoPrecessor extends AbstractProcessor {
    private static final String mRouteMapperFieldName = "routeMapper";
    private static final String mParamsMapperFieldName = "paramsMapper";

    private Logger logger;

    private Filer mFiler;
    private Types types;
    private Elements elements;

    private TypeMirror type_String;

    private ArrayList<Node> routerNodes;
//    private TypeUtils typeUtils;
    private String host = null;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        routerNodes = new ArrayList<>();

        mFiler = processingEnv.getFiler();
        types = processingEnv.getTypeUtils();
        elements = processingEnv.getElementUtils();
//        typeUtils = new TypeUtils(types, elements);

        type_String = elements.getTypeElement("java.lang.String").asType();

        logger = new Logger(processingEnv.getMessager());

//        Map<String, String> options = processingEnv.getOptions();
//        if (MapUtils.isNotEmpty(options)) {
//            host = options.get(KEY_HOST_NAME);
//            logger.info(">>> host is " + host + " <<<");
//        }
        if (host == null || host.equals("")) {
            host = "default";
        }
        logger.info(">>> RouteProcessor init. <<<");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        logger.info(">>> process start... <<<");
        if (CollectionUtils.isNotEmpty(set)) {
            Set<? extends Element> routeNodes = roundEnvironment.getElementsAnnotatedWith(RouteNode.class);
            try {
                logger.info(">>> Found routes, start... <<<");
                parseRouteNodes(routeNodes);
            } catch (Exception e) {
                logger.error(e);
            }
//            generateRouterImpl();
//            generateRouterTable();
            return true;
        }
        return false;
    }

    private void parseRouteNodes(Set<? extends Element> routeElements) {

        TypeMirror type_Activity = elements.getTypeElement(ACTIVITY).asType();

        for (Element element : routeElements) {
            TypeMirror tm = element.asType();
            RouteNode route = element.getAnnotation(RouteNode.class);

            if (types.isSubtype(tm, type_Activity)) {                 // Activity
                logger.info(">>> Found activity route: " + tm.toString() + " <<<");

                Node node = new Node();
                String path = route.path();

                checkPath(path);

                node.setPath(path);
                node.setDesc(route.desc());
                node.setPriority(route.priority());
                node.setNodeType(NodeType.ACTIVITY);
                node.setRawType(element);

                Map<String, Integer> paramsType = new HashMap<>();
                Map<String, String> paramsDesc = new HashMap<>();
                for (Element field : element.getEnclosedElements()) {
//                    if (field.getKind().isField() && field.getAnnotation(Autowired.class) != null) {
//                        Autowired paramConfig = field.getAnnotation(Autowired.class);
//                        paramsType.put(StringUtils.isEmpty(paramConfig.name())
//                                ? field.getSimpleName().toString() : paramConfig.name(), typeUtils.typeExchange(field));
//                        paramsDesc.put(StringUtils.isEmpty(paramConfig.name())
//                                ? field.getSimpleName().toString() : paramConfig.name(), typeUtils.typeDesc(field));
//                    }
                }
                node.setParamsType(paramsType);
                node.setParamsDesc(paramsDesc);

                if (!routerNodes.contains(node)) {
                    routerNodes.add(node);
                }
            } else {
                throw new IllegalStateException("only activity can be annotated by RouteNode");
            }
        }
    }

    private void checkPath(String path) {
        if (path == null || path.isEmpty() || !path.startsWith("/"))
            throw new IllegalArgumentException("path cannot be null or empty,and should start with /,this is:" + path);

        if (path.contains("//") || path.contains("&") || path.contains("?"))
            throw new IllegalArgumentException("path should not contain // ,& or ?,this is:" + path);

        if (path.endsWith("/"))
            throw new IllegalArgumentException("path should not endWith /,this is:" + path
                    + ";or append a token:index");
    }

}
