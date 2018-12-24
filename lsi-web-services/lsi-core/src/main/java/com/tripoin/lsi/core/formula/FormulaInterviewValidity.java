package com.tripoin.lsi.core.formula;

import com.tripoin.lsi.core.data.dto.request.RequestFormulaInterviewValidity;
import com.wissensalt.util.common.formula.AFormulaCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

/**
 * Created on 9/15/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class FormulaInterviewValidity extends AFormulaCalculator<RequestFormulaInterviewValidity, Double> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaInterviewValidity.class);

    @Override
    public void calculate() {
        if (getParam().getNumberOfDataValid() != null && getParam().getNumberOfCompletedInterview() != null) {
            if (getParam().getNumberOfCompletedInterview() != 0) {
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                setResult(
                        Double.valueOf(decimalFormat.format((Double.valueOf(getParam().getNumberOfDataValid())/Double.valueOf(getParam().getNumberOfCompletedInterview())) * 100))
                );
            }else {
                setResult(0D);
            }
        }else {
            setResult(0D);
            LOGGER.error("Formula Validity Error : Number of success interview || number of respondent is null");
        }
    }
}
