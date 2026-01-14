-- ==========================================
-- 1. 初始化插件 (必须最先执行)
-- ==========================================
CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS pgrouting;

-- ==========================================
-- 2. 清理旧表 (防止冲突)
-- ==========================================
DROP TABLE IF EXISTS public.sys_suggestion CASCADE;
DROP TABLE IF EXISTS public.sys_lost_found CASCADE;
DROP TABLE IF EXISTS public.zzu_gongjiao CASCADE;
DROP TABLE IF EXISTS public.zzu_point CASCADE;
DROP TABLE IF EXISTS public.zzu_polygon CASCADE;
DROP TABLE IF EXISTS public.zzu_road CASCADE;
DROP TABLE IF EXISTS public.zzu_road_noded CASCADE;
DROP TABLE IF EXISTS public.zzu_road_noded_vertices_pgr CASCADE;

-- ==========================================
-- 3. 创建业务表：建议反馈 (sys_suggestion)
-- 🔥 修复：显式创建序列，确保 ID 自增
-- ==========================================
DROP SEQUENCE IF EXISTS sys_suggestion_id_seq;
CREATE SEQUENCE sys_suggestion_id_seq;

CREATE TABLE public.sys_suggestion (
                                       id integer NOT NULL DEFAULT nextval('sys_suggestion_id_seq'),
                                       visitor_name character varying(255) DEFAULT '游客',
                                       content text,
                                       create_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                                       CONSTRAINT sys_suggestion_pkey PRIMARY KEY (id)
);

-- ==========================================
-- 4. 创建业务表：失物招领 (sys_lost_found)
-- 🔥 修复：显式创建序列，坐标系为 3857
-- ==========================================
DROP SEQUENCE IF EXISTS sys_lost_found_id_seq;
CREATE SEQUENCE sys_lost_found_id_seq;

CREATE TABLE public.sys_lost_found (
                                       id integer NOT NULL DEFAULT nextval('sys_lost_found_id_seq'),
                                       item_name character varying(255),
                                       description text,
                                       contact character varying(255),
                                       lost_type character varying(50),
                                       status integer DEFAULT 0,
                                       visitor_name character varying(255) DEFAULT '游客',
                                       create_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                                       geom geometry(Point, 3857), -- 注意：这里是米坐标
                                       CONSTRAINT sys_lost_found_pkey PRIMARY KEY (id)
);
CREATE INDEX sys_lost_found_geom_idx ON public.sys_lost_found USING gist (geom);

-- ==========================================
-- 5. GIS表：公交路线 (zzu_gongjiao)
-- ==========================================
CREATE TABLE public.zzu_gongjiao (
                                     id bigint NOT NULL,
                                     geom geometry(MultiLineString, 3857),
                                     name character varying(80),
                                     stops character varying(254),
                                     schedule character varying(100),
                                     CONSTRAINT zzu_gongjiao_pkey PRIMARY KEY (id)
);
CREATE INDEX sidx_zzu_gongjiao_geom ON public.zzu_gongjiao USING gist (geom);

-- ==========================================
-- 6. GIS表：兴趣点 (zzu_point)
-- ==========================================
DROP SEQUENCE IF EXISTS zzu_point_gid_seq;
CREATE SEQUENCE zzu_point_gid_seq;

CREATE TABLE public.zzu_point (
                                  gid integer NOT NULL DEFAULT nextval('zzu_point_gid_seq'),
                                  fid numeric,
                                  name character varying(20),
                                  geom geometry(Point, 4326), -- 注意：这里是经纬度
                                  CONSTRAINT zzu_point_pkey PRIMARY KEY (gid)
);
CREATE INDEX zzu_point_geom_idx ON public.zzu_point USING gist (geom);

-- ==========================================
-- 7. GIS表：建筑物/区域 (zzu_polygon)
-- ==========================================
DROP SEQUENCE IF EXISTS zzu_polygon_gid_seq;
CREATE SEQUENCE zzu_polygon_gid_seq;

CREATE TABLE public.zzu_polygon (
                                    gid integer NOT NULL DEFAULT nextval('zzu_polygon_gid_seq'),
                                    fid numeric,
                                    dn numeric,
                                    name character varying(21),
                                    layer character varying(254),
                                    path character varying(254),
                                    geom geometry(MultiPolygon, 4326), -- 注意：这里是经纬度
                                    CONSTRAINT zzu_polygon_pkey PRIMARY KEY (gid)
);
CREATE INDEX zzu_polygon_geom_idx ON public.zzu_polygon USING gist (geom);

-- ==========================================
-- 8. GIS表：原始路网 (zzu_road)
-- ==========================================
DROP SEQUENCE IF EXISTS zzu_road_gid_seq;
CREATE SEQUENCE zzu_road_gid_seq;

CREATE TABLE public.zzu_road (
                                 gid integer NOT NULL DEFAULT nextval('zzu_road_gid_seq'),
                                 fid numeric,
                                 geom geometry(MultiLineString, 3857),
                                 CONSTRAINT zzu_road_pkey PRIMARY KEY (gid)
);
CREATE INDEX zzu_road_geom_idx ON public.zzu_road USING gist (geom);

-- ==========================================
-- 9. 核心表：路径规划拓扑路网 (zzu_road_noded)
-- 🔥 这是路径规划的核心表，必须包含 source/target/length
-- ==========================================
DROP SEQUENCE IF EXISTS zzu_road_noded_id_seq;
CREATE SEQUENCE zzu_road_noded_id_seq;

CREATE TABLE public.zzu_road_noded (
                                       id integer NOT NULL DEFAULT nextval('zzu_road_noded_id_seq'),
                                       geom geometry,
                                       source integer,
                                       target integer,
                                       length double precision,
                                       CONSTRAINT zzu_road_noded_pkey PRIMARY KEY (id)
);
CREATE INDEX zzu_road_noded_geom_idx ON public.zzu_road_noded USING gist (geom);

-- ==========================================
-- 10. 核心表：路径规划顶点 (zzu_road_noded_vertices_pgr)
-- ==========================================
CREATE TABLE public.zzu_road_noded_vertices_pgr (
                                                    id bigint NOT NULL,
                                                    geom geometry,
                                                    CONSTRAINT zzu_road_noded_vertices_pgr_pkey PRIMARY KEY (id)
);
CREATE INDEX zzu_road_noded_vertices_pgr_geom_idx ON public.zzu_road_noded_vertices_pgr USING gist (geom);