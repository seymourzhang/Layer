DROP FUNCTION IF EXISTS sparrow_lc.s_f_getDayAgeByHouseId;
CREATE DEFINER=`phoelc`@`%` FUNCTION `s_f_getDayAgeByHouseId`(in_house_id int,in_date DATETIME) RETURNS int(11)
BEGIN
  DECLARE cur_day_age int;
  SELECT a.day_age into cur_day_age from s_b_layer_breed_detail a where 1=1
    and exists(SELECT 1 from s_b_layer_house_breed b where a.house_breed_id = b.id and b.house_id = in_house_id)
    and date_format(a.growth_date,'%Y-%m-%d')  = date_format(in_date,'%Y-%m-%d')
    LIMIT 1;
  RETURN(cur_day_age);
END;