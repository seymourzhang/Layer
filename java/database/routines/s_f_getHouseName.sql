DROP FUNCTION IF EXISTS sparrow_lc.s_f_getHouseName;
CREATE DEFINER=`phoelc`@`%` FUNCTION `s_f_getHouseName`(in_house_id int ) RETURNS varchar(100) CHARSET utf8
BEGIN
  DECLARE out_house_name VARCHAR(100);
  SELECT house_name INTO  out_house_name FROM s_d_house where id = in_house_id ;
  RETURN(out_house_name);
END;