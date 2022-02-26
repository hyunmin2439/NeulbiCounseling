package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 *
 * Query를 던질 때
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 846542477L;

    public static final QUser user = new QUser("user");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited : BaseEntity 상속받는 Entity
    public final NumberPath<Long> id = _super.id;

    public final StringPath roleId = createString("roleId");

    public final StringPath maskId = createString("maskId");

    public final StringPath nickname = createString("nickname");

    public final StringPath email = createString("email");

    public final StringPath password = createString("password");

    public final StringPath isConsultant = createString("isConsultant");

    public final StringPath pointTot = createString("pointTot");

    public final StringPath profileImg = createString("profileImg");

    public final StringPath isPenalty = createString("isPenalty");

    public final StringPath reportCnt = createString("reportCnt");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

