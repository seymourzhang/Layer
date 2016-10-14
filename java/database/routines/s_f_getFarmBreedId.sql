DROP FUNCTION IF EXISTS sparrow_lc.s_f_getFarmBreedId;
CREATE DEFINER=`phoelc`@`%` FUNCTION `s_f_getFarmBreedId`(in_farm_id int ) RETURNS int(11)
BEGIN
  DECLARE farm_breed_id int;
  SELECT id INTO  farm_breed_id FROM (select id from s_b_layer_farm_breed where 1=1 and farm_id = in_farm_id order by place_date desc) data  LIMIT 1 ;
  IF ISNULL(farm_breed_id)  THEN
    SET farm_breed_id = 0;
  END if;
  RETURN(farm_breed_id);
END;