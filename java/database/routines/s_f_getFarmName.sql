DROP FUNCTION IF EXISTS sparrow_lc.s_f_getFarmName;
CREATE DEFINER=`phoelc`@`%` FUNCTION `s_f_getFarmName`(in_farm_id int ) RETURNS varchar(100) CHARSET utf8
BEGIN
  DECLARE out_farm_name VARCHAR(100);
  SELECT farm_name_chs INTO  out_farm_name FROM s_d_farm where id = in_farm_id ;
  RETURN(out_farm_name);
END;