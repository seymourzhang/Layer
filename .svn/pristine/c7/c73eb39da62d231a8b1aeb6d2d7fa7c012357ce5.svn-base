DROP FUNCTION IF EXISTS sparrow_lc.s_f_getCodeName;
CREATE DEFINER=`phoelc`@`%` FUNCTION `s_f_getCodeName`(in_code_type VARCHAR(50),in_biz_code VARCHAR(100)) RETURNS varchar(200) CHARSET utf8
BEGIN
  DECLARE out_code_name VARCHAR(200);
  SELECT a.code_name INTO out_code_name FROM s_d_code a where 1=1
    and a.code_type = in_code_type
    and a.biz_code = in_biz_code;
  RETURN(out_code_name);
END;